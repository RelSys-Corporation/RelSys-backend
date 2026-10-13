package com.domain.revenue.person.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.br.CPF;

import java.util.List;

public record PhysicalPersonInputDto(
        @NotNull(message = "O nome deve ser informado.")
        String name,
        @Email
        String email,
        @NotNull(message = "O CPF deve ser informado.")
        @NotBlank(message = "O CPF deve ser informado.")
        @CPF
        String cpf,
        List<String> personRoles
) {
}
