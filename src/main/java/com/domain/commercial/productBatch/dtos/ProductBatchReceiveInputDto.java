package com.domain.commercial.productBatch.dtos;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record ProductBatchReceiveInputDto(
        Long productId,
        Long supplierId,
        BigDecimal purchasePrice,
        BigDecimal quantity,
        Long warehouseId,
        Long warehouseLocationId,
        LocalDateTime expirationDate) {
}