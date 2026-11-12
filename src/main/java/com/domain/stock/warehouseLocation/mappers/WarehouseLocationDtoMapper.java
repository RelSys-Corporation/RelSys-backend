package com.domain.stock.warehouseLocation.mappers;

import com.domain.stock.warehouseLocation.WarehouseLocation;
import com.domain.stock.warehouseLocation.dtos.WarehouseLocationOutputDto;

public class WarehouseLocationDtoMapper {
    public static WarehouseLocationOutputDto toDto(WarehouseLocation entity) {
        return new WarehouseLocationOutputDto(
                entity.id,
                entity.warehouse.id,
                entity.street,
                entity.rack,
                entity.level,
                entity.position,
                entity.active
        );
    }
}