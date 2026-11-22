package com.domain.auth.permissions.services;

import com.domain.auth.authManager.dtos.PermissionsOutputDto;
import com.domain.auth.permissions.Permissions;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
public class PermissionsService {
    public List<PermissionsOutputDto> getMethodsByPermissions(List<Permissions> permissions) {
        return permissions.stream()
                .collect(Collectors.groupingBy(
                        p -> p.entity.name,
                        Collectors.mapping(
                                p -> p.methodName,
                                Collectors.toSet()
                        )
                ))
                .entrySet().stream()
                .map(entry -> new PermissionsOutputDto(entry.getKey(), List.copyOf(entry.getValue())))
                .toList();
    }
}