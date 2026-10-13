package com.domain.revenue.person.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.br.CNPJ;

import java.util.List;

public record LegalPersonInputDto(
        @NotNull(message = "O nome deve ser informado.")
        String name,
        @Email
        String email,
        @NotNull(message = "O CNPJ deve ser informado.")
        @NotBlank(message = "O CNPJ deve ser informado.")
        @CNPJ
        String cnpj,
        List<String> personRoles
) {
}
