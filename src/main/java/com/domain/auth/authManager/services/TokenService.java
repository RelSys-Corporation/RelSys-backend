package com.domain.auth.authManager.services;

import com.domain.auth.rolesPermissions.RolesPermissions;
import com.domain.auth.users.Users;
import com.domain.system.company.Company;
import io.smallrye.jwt.build.Jwt;
import jakarta.enterprise.context.ApplicationScoped;

import java.time.Duration;
import java.util.Set;
import java.util.stream.Collectors;

@ApplicationScoped
public class TokenService {
    public static String generateToken(Users user, Company company) {
        Set<String> authorities = RolesPermissions.getPermissionByRole(user.role)
                .stream()
                .map(p -> p.name)
                .collect(Collectors.toSet());

        authorities.add(user.role.name);

        return Jwt.issuer(System.getenv("JWT_ISSUER"))
                .upn(user.name)
                .claim("companyId", company.id)
                .groups(authorities)
                .expiresIn(Duration.ofHours(8))
                .sign();
    }
}