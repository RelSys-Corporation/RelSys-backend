package com.domain.auth.authManager.dtos;

public record RegisterInputDto(
        String userName,
        String userPassword,
        Long roleId
) {
}
