package com.domain.revenue.product.batch.productBatch.mappers;

import com.domain.revenue.product.batch.productBatch.ProductBatch;
import com.domain.revenue.product.batch.productBatch.dtos.ProductBatchReceiveOutputDto;

public class ProductBatchDtoMapper {
    public static ProductBatchReceiveOutputDto toDto(ProductBatch entity) {
        return new ProductBatchReceiveOutputDto(entity.id);
    }
}
