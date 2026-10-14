package com.domain.auth.rolesPermissions.services;

import com.domain.auth.permissions.Permissions;
import com.domain.auth.roles.Roles;
import com.domain.auth.rolesPermissions.RolesPermissions;
import com.domain.auth.rolesPermissions.dtos.PermissionsInputDto;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@ApplicationScoped
public class RolesPermissionsService {
    public List<RolesPermissions> givePermissionsToRole(
            Long roleId,
            List<PermissionsInputDto> permissionsDto
    ) {
        if (permissionsDto == null || permissionsDto.isEmpty()) {
            return Collections.emptyList();
        }

        List<RolesPermissions> rolesPermissions = new ArrayList<>();

        Roles role = Roles.getByIdOrThrow(roleId);

        Set<Long> existingPermissionsIds = RolesPermissions.getPermissionByRole(role)
                .stream()
                .map(p -> p.id)
                .collect(Collectors.toSet());

        Set<Permissions> permissionsToGive = permissionsDto.stream()
                .map(PermissionsInputDto::id)
                .filter(id -> !existingPermissionsIds.contains(id))
                .map(Permissions::getByIdOrThrow)
                .collect(Collectors.toSet());

        for (Permissions permission : permissionsToGive) {
            rolesPermissions.add(
                    RolesPermissions.create(
                            role,
                            permission
                    )
            );
        }

        return rolesPermissions;
    }
}
