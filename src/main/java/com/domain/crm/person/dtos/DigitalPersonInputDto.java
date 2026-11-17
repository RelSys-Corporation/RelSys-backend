package com.domain.crm.person.dtos;

import com.domain.system.message.Message;
import com.infrastructure.annotations.RequiredField;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record DigitalPersonInputDto(
        @RequiredField(entity = Message.Entity.PERSON, property = Message.Property.NAME)
        String name,
        @Email
        String email,
        List<String> personRoles
) {
}
