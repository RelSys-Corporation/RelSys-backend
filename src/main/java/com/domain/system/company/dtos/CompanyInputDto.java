package com.domain.system.company.dtos;

import jakarta.validation.constraints.NotNull;
import jakarta.ws.rs.DefaultValue;

public record CompanyInputDto(
    @NotNull(message = "Deve ser informado o nome corporativo da empresa.")
    String corporateName,
    String tradeName,
    @NotNull(message = "Deve ser informada se a empresa é matriz.")
    boolean isHeadquarter,
    Long companyId
) {
}
