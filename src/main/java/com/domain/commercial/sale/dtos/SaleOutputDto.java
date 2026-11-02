package com.domain.commercial.sale.dtos;

import io.quarkus.runtime.annotations.RegisterForReflection;

import java.time.LocalDateTime;

@RegisterForReflection
public record SaleOutputDto(
        Long id,
        String clientName,
        String paymentMethod,
        LocalDateTime timestamp
) {
}
