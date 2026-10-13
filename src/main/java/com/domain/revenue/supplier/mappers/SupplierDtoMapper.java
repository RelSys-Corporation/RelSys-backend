package com.domain.revenue.supplier.mappers;

import com.domain.revenue.supplier.Supplier;
import com.domain.revenue.supplier.dtos.SupplierOutputDto;

public class SupplierDtoMapper {
    public static SupplierOutputDto toDto(Supplier entity) {
        return new SupplierOutputDto(entity.id, entity.person.name);
    }
}
