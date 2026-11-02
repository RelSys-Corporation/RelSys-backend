package com.domain.stock.productBatch.mappers;

import com.domain.stock.productBatch.ProductBatch;
import com.domain.stock.productBatch.dtos.ProductBatchReceiveOutputDto;

public class ProductBatchDtoMapper {
    public static ProductBatchReceiveOutputDto toDto(ProductBatch entity) {
        return new ProductBatchReceiveOutputDto(entity.id);
    }
}
