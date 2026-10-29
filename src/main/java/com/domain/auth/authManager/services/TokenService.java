package com.domain.auth.authManager.services;

import com.domain.auth.rolesPermissions.RolesPermissions;
import com.domain.auth.users.Users;
import com.domain.system.company.Company;
import io.smallrye.jwt.build.Jwt;
import jakarta.enterprise.context.ApplicationScoped;

import java.time.Duration;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@ApplicationScoped
public class TokenService {
    public String generateToken(Users user, Company company) {
        Set<String> authorities = Set.of(user.role.name);

        return Jwt.issuer(System.getenv("JWT_ISSUER"))
                .subject(user.id.toString())
                .upn(user.name)
                .claim("companyId", (company == null ? 0 : company.id))
                .groups(authorities)
                .expiresIn(Duration.ofHours(8))
                .sign();
    }
}