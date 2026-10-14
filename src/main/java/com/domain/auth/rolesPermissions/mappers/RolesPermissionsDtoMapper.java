package com.domain.auth.rolesPermissions.mappers;

import com.domain.auth.rolesPermissions.RolesPermissions;
import com.domain.auth.rolesPermissions.dtos.RolesPermissionsOutputDto;

import java.util.List;

public class RolesPermissionsDtoMapper {
    public static List<RolesPermissionsOutputDto> toDto(
            List<RolesPermissions> permissions
    ) {
        return permissions.stream()
                .map(rp -> rp.permission.id)
                .map(RolesPermissionsOutputDto::new)
                .toList();
    }
}
