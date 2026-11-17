package com.domain.commercial.productBatch;

import com.domain.commercial.product.suppliers.productSupplier.ProductSupplier;
import com.domain.shared.BaseEntityCompany;
import com.domain.system.message.Message;
import com.infrastructure.exceptions.DomainException;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "PRODUCT_BATCH", schema = "COMMERCIAL")
public class ProductBatch extends BaseEntityCompany {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PRODUCT_SUPPLIER_ID", nullable = false)
    public ProductSupplier productSupplier;

    @Column(name = "PURCHASE_PRICE", scale = 15, precision = 3, nullable = false)
    public BigDecimal purchasePrice;

    @Column(name = "EXPIRATION_DATE")
    public LocalDateTime expirationDate;

    protected ProductBatch() {}

    private ProductBatch(
            ProductSupplier productSupplier,
            BigDecimal purchasePrice,
            LocalDateTime expirationDate) {
        if (productSupplier == null)
            throw new DomainException(Message.format(Message.Type.REQUIRED_ARGUMENT, Message.Entity.PRODUCT_SUPPLIER));

        if (purchasePrice == null || purchasePrice.compareTo(BigDecimal.ZERO) < 0)
            throw new DomainException(Message.format(Message.Type.SHOULD_HAVE, Message.Entity.PRODUCT_BATCH, Message.Property.PRICE, "maior ou igual a 0"));

        this.productSupplier = productSupplier;
        this.purchasePrice = purchasePrice;
        this.expirationDate = expirationDate;
    }

    public static ProductBatch create(
            ProductSupplier productSupplier,
            BigDecimal purchasePrice,
            LocalDateTime expirationDate) {
        ProductBatch productBatch = new ProductBatch(
                productSupplier,
                purchasePrice,
                expirationDate
        );

        productBatch.persist();

        return productBatch;
    }
}