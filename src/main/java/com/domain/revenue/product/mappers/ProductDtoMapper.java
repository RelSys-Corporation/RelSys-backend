package com.domain.revenue.product.mappers;

import com.domain.revenue.product.Product;
import com.domain.revenue.product.dtos.ProductInputDto;
import com.domain.revenue.product.dtos.ProductOutputDto;
import com.domain.revenue.supplier.Supplier;
import com.domain.revenue.supplier.mappers.SupplierDtoMapper;

import java.util.List;

public class ProductDtoMapper {
    public static ProductOutputDto toDto (Product entity) {
        return new ProductOutputDto(
                entity.id,
                entity.name,
                entity.price,
                entity.barcode.value()
        );
    }
}
