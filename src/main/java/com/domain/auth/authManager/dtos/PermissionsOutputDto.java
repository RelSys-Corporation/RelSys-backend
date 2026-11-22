package com.domain.auth.authManager.dtos;

import java.util.List;

public record PermissionsOutputDto(
        String entity,
        List<String> actions
) {
}
