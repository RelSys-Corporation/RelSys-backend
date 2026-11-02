package com.domain.revenue.product.dtos;

import io.quarkus.runtime.annotations.RegisterForReflection;

import java.math.BigDecimal;

@RegisterForReflection
public record ProductOutputDto(
        Long id,
        String name,
        BigDecimal price,
        String barcode
) {
}
