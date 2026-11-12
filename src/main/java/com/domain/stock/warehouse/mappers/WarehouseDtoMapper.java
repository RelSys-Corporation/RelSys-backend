package com.domain.stock.warehouse.mappers;

import com.domain.stock.warehouse.Warehouse;
import com.domain.stock.warehouse.dtos.WarehouseOutputDto;

public class WarehouseDtoMapper {
    public static WarehouseOutputDto toDto(Warehouse entity) {
        return new WarehouseOutputDto(
                entity.id,
                entity.name
        );
    }
}