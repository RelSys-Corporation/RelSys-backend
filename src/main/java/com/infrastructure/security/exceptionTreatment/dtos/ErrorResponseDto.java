package com.infrastructure.security.exceptionTreatment.dtos;

import io.quarkus.runtime.annotations.RegisterForReflection;

import java.time.LocalDateTime;

@RegisterForReflection
public record ErrorResponseDto(
        String errorMessage,
        int status,
        LocalDateTime timestamp) {
    public ErrorResponseDto(String errorMessage, int status) {
        this(errorMessage, status, LocalDateTime.now());
    }
}
