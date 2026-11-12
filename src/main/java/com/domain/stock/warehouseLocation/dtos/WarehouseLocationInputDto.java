package com.domain.stock.warehouseLocation.dtos;

public record WarehouseLocationInputDto(
        String street,
        String rack,
        String level,
        String position,
        Boolean active) {
}