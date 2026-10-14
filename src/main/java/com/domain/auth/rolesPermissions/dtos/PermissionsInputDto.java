package com.domain.auth.rolesPermissions.dtos;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record PermissionsInputDto(
        @NotNull(message = "A permissão deve ser enviada.")
        Long id
) {
}
