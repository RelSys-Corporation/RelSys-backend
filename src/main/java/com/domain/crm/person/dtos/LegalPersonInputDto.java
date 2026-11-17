package com.domain.crm.person.dtos;

import com.domain.system.message.Message;
import com.infrastructure.annotations.RequiredField;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.br.CNPJ;

import java.util.List;

public record LegalPersonInputDto(
        @RequiredField(entity = Message.Entity.PERSON, property = Message.Property.NAME)
        String name,
        @Email
        String email,
        @RequiredField(entity = Message.Entity.CNPJ)
        @CNPJ
        String cnpj,
        List<String> personRoles
) {
}
