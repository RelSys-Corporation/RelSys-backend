package com.domain.commercial.productBatch.services;

import com.domain.commercial.product.Product;
import com.domain.commercial.product.suppliers.productSupplier.ProductSupplier;
import com.domain.commercial.product.suppliers.productSupplier.services.ProductSupplierService;
import com.domain.crm.person.subtype.Supplier;
import com.domain.commercial.productBatch.ProductBatch;
import com.domain.stock.stockMovement.services.StockMovementService;
import com.domain.stock.warehouse.Warehouse;
import com.domain.stock.warehouseLocation.WarehouseLocation;
import com.infrastructure.exceptions.DomainException;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@ApplicationScoped
public class ProductBatchService {
    @Inject
    StockMovementService stockMovementService;

    public ProductBatch createProductBatch(
            Long productId,
            Long supplierId,
            BigDecimal purchasePrice,
            BigDecimal quantity,
            Long warehouseId,
            Long warehouseLocationId,
            LocalDateTime expirationDate
    ) {
        Product product = Product.getByIdOrThrow(productId);
        Supplier supplier = Supplier.getByIdOrThrow(supplierId);

        ProductSupplier productSupplier = ProductSupplierService.getByIdOrThrow(product, supplier);

        Warehouse warehouse = Warehouse.getByIdOrThrow(warehouseId);
        WarehouseLocation warehouseLocation = WarehouseLocation.getByIdOrThrow(warehouseLocationId);

        if (!warehouseLocation.warehouse.id.equals(warehouse.id))
            throw new DomainException("A localização informada não pertence ao armazém informado.");

        ProductBatch productBatch = ProductBatch.create(
                productSupplier,
                purchasePrice,
                expirationDate
        );

        stockMovementService.createPurchaseReceiptMovement(
                productBatch,
                product,
                warehouse,
                warehouseLocation,
                quantity
        );

        return productBatch;
    }

    public List<ProductBatch> getProductBatchWithStockByProduct(Product product) {
        List<ProductBatch> batches = ProductBatch.list(
                "productSupplier.product.id = ?1 order by expirationDate nulls last",
                product.id
        );

        return batches.stream()
                .filter(batch -> stockMovementService.getAvailableQuantityByBatch(batch).compareTo(BigDecimal.ZERO) > 0)
                .toList();
    }
}