package com.domain.revenue.sale.productSale.productSaleBatch;

import com.domain.revenue.product.batch.productBatch.ProductBatch;
import com.domain.revenue.sale.productSale.ProductSale;
import com.domain.shared.BaseEntity;
import com.domain.shared.BaseEntityCompany;
import com.infrastructure.exceptions.DomainException;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "PRODUCT_SALE_BATCH", schema = "REVENUE")
public class ProductSaleBatch extends BaseEntityCompany {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PRODUCT_SALE_ID", nullable = false)
    public ProductSale productSale;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PRODUCT_BATCH_ID")
    public ProductBatch productBatch;

    @Column(name = "QUANTITY", scale = 15, precision = 3, nullable = false)
    public BigDecimal quantity;

    @Column(name = "APPLY_DISCOUNT", nullable = false)
    public boolean applyDiscount;

    @Column(name = "PURCHASE_PRICE", scale = 15, precision = 3, nullable = false)
    public BigDecimal purchasePrice;

    protected ProductSaleBatch() {}

    private ProductSaleBatch(
            ProductSale productSale,
            ProductBatch productBatch,
            BigDecimal quantity,
            boolean applyDiscount
    ) {
        if (productSale == null)
            throw new DomainException("Deve ser informado o produto da venda para retirada do lote.");

        if (productBatch == null)
            throw new DomainException("Deve ser informado o lote vinculado ao produto.");

        if (quantity == null || quantity.compareTo(BigDecimal.ZERO) <= 0)
            throw new DomainException("A quantidade de retirada do lote deve ser informada e ser superior a 0.");

        this.productSale = productSale;
        this.productBatch = productBatch;
        this.quantity = quantity;
        this.applyDiscount = applyDiscount;
        this.purchasePrice = productBatch.purchasePrice;
    }

    public static ProductSaleBatch create(
            ProductSale productSale,
            ProductBatch productBatch,
            BigDecimal quantity,
            boolean applyDiscount
    ) {
        ProductSaleBatch productSaleBatch = new ProductSaleBatch(
                productSale,
                productBatch,
                quantity,
                applyDiscount
        );
        productSaleBatch.persist();

        return productSaleBatch;
    }
}
