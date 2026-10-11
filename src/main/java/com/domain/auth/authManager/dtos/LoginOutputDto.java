package com.domain.auth.authManager.dtos;

import io.quarkus.runtime.annotations.RegisterForReflection;

@RegisterForReflection
public record LoginOutputDto(
        String token
) {
}
