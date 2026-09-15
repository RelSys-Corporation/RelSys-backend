package com.domain.revenue.product.dtos;

import com.domain.revenue.supplier.dtos.SupplierOutputDto;
import io.quarkus.runtime.annotations.RegisterForReflection;

import java.math.BigDecimal;
import java.util.List;

@RegisterForReflection
public record ProductOutputDto(
        Long id,
        List<SupplierOutputDto> suppliers,
        String name,
        BigDecimal price,
        String barcode
) {
}
