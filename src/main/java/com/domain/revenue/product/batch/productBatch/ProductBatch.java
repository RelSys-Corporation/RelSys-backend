package com.domain.revenue.product.batch.productBatch;

import com.domain.revenue.product.suppliers.productSupplier.ProductSupplier;
import com.domain.shared.BaseEntity;
import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "PRODUCT_BATCH")
public class ProductBatch extends BaseEntity {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PRODUCT_SUPPLIER_ID", nullable = false)
    public ProductSupplier productSupplier;

    @Column(name = "PURCHASE_PRICE", scale = 15, precision = 3, nullable = false)
    public BigDecimal purchasePrice;

    @Column(name = "PURCHASE_QUANTITY", scale = 15, precision = 3, nullable = false)
    public BigDecimal purchaseQuantity;

    @Column(name = "REMAINING_QUANTITY", scale = 15, precision = 3, nullable = false)
    public BigDecimal remainingQuantity;

    public ProductBatch() {}

    public ProductBatch(
            ProductSupplier productSupplier,
            BigDecimal purchasePrice,
            BigDecimal purchaseQuantity) {
        if (productSupplier == null)
            throw new IllegalArgumentException("O produto de fornecedor deve ser informado.");

        if (purchasePrice.compareTo(BigDecimal.ZERO) < 0)
            throw new IllegalArgumentException("O valor de compra deve ser igual ou mairo do que 0.");

        if (purchaseQuantity.compareTo(BigDecimal.ZERO) <= 0)
            throw new IllegalArgumentException("A quantidade de compra deve ser maior do que 0.");

        this.productSupplier = productSupplier;
        this.purchasePrice = purchasePrice;
        this.purchaseQuantity = purchaseQuantity;
        this.remainingQuantity = purchaseQuantity;
    }

    public static ProductBatch create(ProductSupplier productSupplier, BigDecimal purchasePrice, BigDecimal purchaseQuantity) {
        ProductBatch productBatch = new ProductBatch(
                productSupplier,
                purchasePrice,
                purchaseQuantity);

        productBatch.persist();

        return productBatch;
    }
}
