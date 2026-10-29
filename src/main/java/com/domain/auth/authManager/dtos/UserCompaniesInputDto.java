package com.domain.auth.authManager.dtos;

import java.util.List;

public record UserCompaniesInputDto(
        Long userId,
        List<Long> companiesId
) {
}
