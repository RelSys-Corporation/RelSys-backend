package com.domain.auth.authManager.services;

import com.domain.auth.roles.Roles;
import com.domain.auth.users.Users;
import com.domain.system.company.Company;
import com.infrastructure.exceptions.InvalidCredentialException;
import com.infrastructure.security.utils.HashUtils;
import io.quarkus.hibernate.orm.panache.Panache;
import jakarta.enterprise.context.ApplicationScoped;
import org.hibernate.Session;

@ApplicationScoped
public class AuthService {
    public static Users register(String userName, String userPassword, Long roleId) {
        Roles role = Roles.getByIdOrThrow(roleId);

        String hashedPassword = HashUtils.encode(userPassword);

        return Users.create(
                userName,
                hashedPassword,
                role
        );
    }

    public static String processLogin(String userName, String userPassword, Long companyId) {
        Users user = Users.getByName(userName);

        if (user == null || !HashUtils.verify(user.password, userPassword)) {
            throw new InvalidCredentialException();
        }

        Company company = Company.getById(companyId);

        return TokenService.generateToken(user, company);
    }
}