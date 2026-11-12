package com.domain.stock.stockMovement.dtos;

import io.quarkus.runtime.annotations.RegisterForReflection;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@RegisterForReflection
public record ProductBatchStockOutputDto(
        Long batchId,
        Long productId,
        Long supplierId,
        String supplierName,
        BigDecimal purchasePrice,
        LocalDateTime expirationDate,
        BigDecimal availableQuantity) {
}