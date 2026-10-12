package com.domain.revenue.sale.dtos;

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
