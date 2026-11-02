package com.domain.revenue.product.dtos;

import java.math.BigDecimal;

public record ProductInputDto(
    String name,
    Long supplierId,
    String barcode,
    BigDecimal price
) {
}
