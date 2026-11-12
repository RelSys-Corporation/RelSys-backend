package com.domain.commercial.productBatch.mappers;

import com.domain.commercial.productBatch.ProductBatch;
import com.domain.commercial.productBatch.dtos.ProductBatchReceiveOutputDto;

public class ProductBatchDtoMapper {
    public static ProductBatchReceiveOutputDto toDto(ProductBatch entity) {
        return new ProductBatchReceiveOutputDto(
                entity.id,
                entity.productSupplier.product.id,
                entity.purchasePrice,
                entity.expirationDate
        );
    }
}