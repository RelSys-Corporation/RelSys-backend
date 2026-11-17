package com.domain.auth.rolesPermissions.dtos;

import com.domain.system.message.Message;
import com.infrastructure.annotations.RequiredField;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record PermissionsInputDto(
        @RequiredField(entity = Message.Entity.PERMISSION)
        Long id
) {
}
