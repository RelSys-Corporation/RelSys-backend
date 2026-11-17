package com.domain.system.company.dtos;

import com.domain.system.message.Message;
import com.infrastructure.annotations.RequiredField;
import jakarta.validation.constraints.NotNull;
import jakarta.ws.rs.DefaultValue;

public record CompanyInputDto(
    @RequiredField(entity = Message.Entity.COMPANY, property = Message.Property.NAME)
    String corporateName,
    String tradeName,
    boolean isHeadquarter,
    Long companyId
) {
}
