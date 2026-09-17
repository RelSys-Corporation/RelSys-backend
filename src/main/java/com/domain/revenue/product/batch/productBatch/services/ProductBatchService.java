package com.domain.revenue.product.batch.productBatch.services;

import com.domain.revenue.product.Product;
import com.domain.revenue.product.batch.productBatch.ProductBatch;
import com.domain.revenue.product.suppliers.productSupplier.ProductSupplier;
import com.domain.revenue.supplier.Supplier;
import com.infrastructure.exceptions.DomainException;
import jakarta.enterprise.context.ApplicationScoped;
import com.infrastructure.exceptions.NotFoundException;

import java.math.BigDecimal;
import java.util.Optional;

@ApplicationScoped
public class ProductBatchService {
    public static ProductBatch createProductBatch(
            Long productId,
            Long supplierId,
            BigDecimal purchasePrice,
            BigDecimal purchaseQuantity
    ) {
        ProductSupplier productSupplier = ProductSupplier.findByIdTreated(productId, supplierId);

        return ProductBatch.create(
                productSupplier,
                purchasePrice,
                purchaseQuantity
        );
    }
}
