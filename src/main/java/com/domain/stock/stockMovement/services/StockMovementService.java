package com.domain.stock.stockMovement.services;

import com.domain.commercial.product.Product;
import com.domain.stock.movementType.MovementType;
import com.domain.commercial.productBatch.ProductBatch;
import com.domain.stock.stockMovement.StockMovement;
import com.domain.stock.stockMovementOrigin.StockMovementOrigin;
import com.domain.stock.warehouse.Warehouse;
import com.domain.stock.warehouseLocation.WarehouseLocation;
import com.domain.system.message.Message;
import com.infrastructure.exceptions.DomainException;
import jakarta.enterprise.context.ApplicationScoped;

import java.math.BigDecimal;
import java.util.List;

@ApplicationScoped
public class StockMovementService {
    private static final String FLUX_INPUT = "I";
    private static final String FLUX_OUTPUT = "O";

    private static final String MOVEMENT_PURCHASE_RECEIPT = "PURCHASE_RECEIPT";
    private static final String MOVEMENT_SALE_OUT = "SALE_OUT";

    public StockMovement createPurchaseReceiptMovement(
            ProductBatch productBatch,
            Product product,
            Warehouse warehouse,
            WarehouseLocation warehouseLocation,
            BigDecimal quantity) {
        if (productBatch == null || product == null)
            throw new DomainException(Message.format(Message.Type.REQUIRED_ARGUMENT, Message.Entity.BATCH));

        if (!productBatch.productSupplier.product.id.equals(product.id))
            throw new DomainException(Message.format(Message.Type.NOT_FOUND, Message.Entity.BATCH, productBatch.id));

        MovementType movementType = MovementType.getByName(MOVEMENT_PURCHASE_RECEIPT);
        StockMovementOrigin origin = StockMovementOrigin.createForProductBatch(productBatch);

        return StockMovement.create(
                movementType,
                product,
                warehouse,
                warehouseLocation,
                origin,
                productBatch,
                quantity
        );
    }

    public StockMovement createSaleOutMovement(
            Product product,
            ProductBatch productBatch,
            StockMovementOrigin stockMovementOrigin,
            Warehouse warehouse,
            WarehouseLocation warehouseLocation,
            BigDecimal quantity) {
        MovementType movementType = MovementType.getByName(MOVEMENT_SALE_OUT);

        return StockMovement.create(
                movementType,
                product,
                warehouse,
                warehouseLocation,
                stockMovementOrigin,
                productBatch,
                quantity
        );
    }

    public BigDecimal getAvailableQuantityByBatch(ProductBatch productBatch) {
        if (productBatch == null)
            throw new DomainException(Message.format(Message.Type.REQUIRED_ARGUMENT, Message.Entity.BATCH));

        List<StockMovement> movements = StockMovement.list("productBatch.id = ?1", productBatch.id);

        BigDecimal inputQuantity = movements.stream()
                .filter(movement -> FLUX_INPUT.equals(movement.movementType.fluxAcronym))
                .map(movement -> movement.quantity)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal outputQuantity = movements.stream()
                .filter(movement -> FLUX_OUTPUT.equals(movement.movementType.fluxAcronym))
                .map(movement -> movement.quantity)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        return inputQuantity.subtract(outputQuantity);
    }

    public BigDecimal getAvailableQuantityByProduct(Product product) {
        if (product == null)
            throw new DomainException(Message.format(Message.Type.REQUIRED_ARGUMENT, Message.Entity.PRODUCT));

        List<ProductBatch> batches = ProductBatch.list("productSupplier.product.id = ?1", product.id);

        return batches.stream()
                .map(this::getAvailableQuantityByBatch)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public StockMovement getLastInputMovementByBatch(ProductBatch productBatch) {
        if (productBatch == null)
            throw new DomainException(Message.format(Message.Type.REQUIRED_ARGUMENT, Message.Entity.BATCH));

        return StockMovement.<StockMovement>find(
                        "productBatch.id = ?1 and movementType.fluxAcronym = ?2 order by movementDate desc, id desc",
                        productBatch.id,
                        FLUX_INPUT
                )
                .firstResultOptional()
                .orElseThrow(() -> new DomainException(Message.format(Message.Type.NOT_FOUND, Message.Entity.STOCK_MOVEMENT, productBatch.id)));
    }
}