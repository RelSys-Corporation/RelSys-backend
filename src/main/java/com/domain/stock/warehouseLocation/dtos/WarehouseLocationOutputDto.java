package com.domain.stock.warehouseLocation.dtos;

import io.quarkus.runtime.annotations.RegisterForReflection;

@RegisterForReflection
public record WarehouseLocationOutputDto(
        Long id,
        Long warehouseId,
        String street,
        String rack,
        String level,
        String position,
        boolean active) {
}