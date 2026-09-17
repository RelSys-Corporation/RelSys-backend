package com.domain.revenue.product.batch.productBatch.dtos;

import com.domain.revenue.product.Product;
import com.domain.revenue.supplier.Supplier;

import java.math.BigDecimal;

public record ProductBatchReceiveInputDto(
        Long productId,
        Long supplierId,
        BigDecimal purchasePrice,
        BigDecimal quantity) {
}
