package com.domain.revenue.product.mappers;

import com.domain.revenue.product.Product;
import com.domain.revenue.product.dtos.ProductOutputDto;

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
