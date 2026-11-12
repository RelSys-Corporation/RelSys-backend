package com.domain.stock.stockMovement.mappers;

import com.domain.commercial.productBatch.ProductBatch;
import com.domain.stock.stockMovement.dtos.ProductBatchStockOutputDto;

import java.math.BigDecimal;

public class StockMovementDtoMapper {
    public static ProductBatchStockOutputDto toStockDto(ProductBatch entity, BigDecimal availableQuantity) {
        return new ProductBatchStockOutputDto(
                entity.id,
                entity.productSupplier.product.id,
                entity.productSupplier.supplier.id,
                entity.productSupplier.supplier.person.name,
                entity.purchasePrice,
                entity.expirationDate,
                availableQuantity
        );
    }
}