package com.domain.stock.warehouse.dtos;

import io.quarkus.runtime.annotations.RegisterForReflection;

@RegisterForReflection
public record WarehouseOutputDto(
        Long id,
        String name) {
}