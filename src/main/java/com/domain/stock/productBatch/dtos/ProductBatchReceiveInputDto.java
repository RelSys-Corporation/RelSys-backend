package com.domain.revenue.product.batch.productBatch.dtos;

import java.math.BigDecimal;

public record ProductBatchReceiveInputDto(
        Long productId,
        Long supplierId,
        BigDecimal purchasePrice,
        BigDecimal quantity) {
}
