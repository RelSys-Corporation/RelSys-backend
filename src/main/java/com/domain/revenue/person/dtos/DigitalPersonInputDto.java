package com.domain.revenue.person.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record DigitalPersonInputDto(
        @NotNull(message = "O nome deve ser informado.")
        String name,
        @Email
        String email,
        List<String> personRoles
) {
}
