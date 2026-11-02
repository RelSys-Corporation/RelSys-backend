package com.domain.commercial.product.dtos;

import java.math.BigDecimal;

public record ProductInputDto(
    String name,
    Long supplierId,
    String barcode,
    BigDecimal price
) {
}
