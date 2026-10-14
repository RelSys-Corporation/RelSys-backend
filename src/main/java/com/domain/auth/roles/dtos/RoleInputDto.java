package com.domain.auth.roles.dtos;

import jakarta.validation.constraints.NotNull;

public record RoleInputDto(
        @NotNull(message = "Deve ser informada o nome da função.")
        String name
) {
}
