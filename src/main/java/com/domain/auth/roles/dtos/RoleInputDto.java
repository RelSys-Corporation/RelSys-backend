package com.domain.auth.roles.dtos;

import com.domain.system.message.Message;
import com.infrastructure.annotations.RequiredField;

public record RoleInputDto(
        @RequiredField(entity = Message.Entity.ROLE, property = Message.Property.NAME)
        String name
) {
}
