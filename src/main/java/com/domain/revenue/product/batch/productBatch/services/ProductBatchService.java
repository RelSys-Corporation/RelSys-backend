package com.domain.revenue.product.batch.productBatch.services;

import com.domain.revenue.product.Product;
import com.domain.revenue.product.batch.productBatch.ProductBatch;
import com.domain.revenue.product.suppliers.productSupplier.ProductSupplier;
import com.domain.revenue.product.suppliers.productSupplier.services.ProductSupplierService;
import com.domain.revenue.person.subtype.Supplier;
import jakarta.enterprise.context.ApplicationScoped;

import java.math.BigDecimal;
import java.util.List;

@ApplicationScoped
public class ProductBatchService {
    public ProductBatch createProductBatch(
            Long productId,
            Long supplierId,
            BigDecimal purchasePrice,
            BigDecimal purchaseQuantity
    ) {
        Product product = Product.getByIdOrThrow(productId);
        Supplier supplier = Supplier.getByIdOrThrow(supplierId);

        ProductSupplier productSupplier = ProductSupplierService.getByIdOrThrow(product, supplier);

        return ProductBatch.create(
                productSupplier,
                purchasePrice,
                purchaseQuantity
        );
    }

    public List<ProductBatch> getProductBatchWithStockByProduct(Product product) {
        return ProductBatch.list("productSupplier.product.id = ?1 and remainingQuantity > 0 order by expirationDate", product.id);
    }
}
