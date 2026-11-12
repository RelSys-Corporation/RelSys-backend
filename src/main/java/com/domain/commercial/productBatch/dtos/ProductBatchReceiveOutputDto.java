package com.domain.commercial.productBatch.dtos;

import io.quarkus.runtime.annotations.RegisterForReflection;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@RegisterForReflection
public record ProductBatchReceiveOutputDto(
        Long id,
        Long productId,
        BigDecimal purchasePrice,
        LocalDateTime expirationDate) {
}