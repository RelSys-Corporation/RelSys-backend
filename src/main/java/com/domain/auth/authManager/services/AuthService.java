package com.domain.auth.authManager.services;

import com.domain.auth.roles.Roles;
import com.domain.auth.users.Users;
import com.domain.system.company.Company;
import com.infrastructure.exceptions.InvalidCredentialException;
import com.infrastructure.security.utils.HashUtils;
import io.quarkus.hibernate.orm.panache.Panache;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.core.NewCookie;
import org.hibernate.Session;

import java.time.Duration;
import java.time.Instant;
import java.util.Date;

@ApplicationScoped
public class AuthService {
    @Inject
    TokenService tokenService;

    public Users register(String userName, String userPassword, Long roleId) {
        Roles role = Roles.getByIdOrThrow(roleId);

        String hashedPassword = HashUtils.encode(userPassword);

        return Users.create(
                userName,
                hashedPassword,
                role
        );
    }

    public NewCookie processLogin(String userName, String userPassword, Long companyId) {
        Users user = Users.getByName(userName);

        if (user == null || !HashUtils.verify(user.password, userPassword)) {
            throw new InvalidCredentialException();
        }

        Company company = Company.getById(companyId);

        String token = tokenService.generateToken(user, company);

        return new NewCookie.Builder("Bearer")
                .value(token)
                .path("/")
                .httpOnly(true)
                //.secure()
                .sameSite(NewCookie.SameSite.LAX)
                .maxAge(1000 * 60 * 60 * 8)
                .build();
    }
}