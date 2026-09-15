package com.domain.revenue.product.batch.productBatch.dtos;

import com.domain.revenue.product.Product;
import com.domain.revenue.supplier.Supplier;

import java.math.BigDecimal;

public record ProductBatchInputDto(
        Product product,
        Supplier supplier,
        BigDecimal purchasePrice,
        BigDecimal purchaseQuantity) {
}
