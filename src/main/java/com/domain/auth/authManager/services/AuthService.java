package com.domain.auth.authManager.services;

import com.domain.auth.users.Users;
import com.domain.system.company.Company;
import com.infrastructure.exceptions.InvalidCredentialException;
import com.infrastructure.security.utils.HashUtils;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class AuthService {
    public static Users register(String userName, String userPassword) {
        String hashedPassword = HashUtils.encode(userPassword);

        return Users.create(
                userName,
                hashedPassword
        );
    }

    public static String processLogin(String userName, String userPassword, Long companyId) {
        Company company = Company.getByIdOrThrow(companyId);

        Users user = Users.find(
                "name = ?1",
                userName
        ).firstResult();

        String hashedPassword = HashUtils.encode(userPassword);

        if (user == null || !HashUtils.verify(hashedPassword, user.password))
            throw new InvalidCredentialException();

        return TokenService.generateToken(user, company);
    }
}