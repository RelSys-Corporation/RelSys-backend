package com.domain.auth.authManager.dtos;

public record LoginInputDto(
        String userName,
        String userPassword,
        Long companyId
) {
}
