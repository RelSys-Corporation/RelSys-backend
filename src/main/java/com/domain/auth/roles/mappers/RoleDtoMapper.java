package com.domain.auth.roles.mappers;

import com.domain.auth.roles.Roles;
import com.domain.auth.roles.dtos.RoleOutputDto;

public class RoleDtoMapper {
    public static RoleOutputDto toDto(Roles role) {
        return new RoleOutputDto(
                role.id
        );
    }
}
