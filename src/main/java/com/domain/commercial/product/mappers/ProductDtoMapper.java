package com.domain.commercial.product.mappers;

import com.domain.commercial.product.Product;
import com.domain.commercial.product.dtos.ProductOutputDto;

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
