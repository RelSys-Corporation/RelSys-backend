package com.domain.revenue.sale.productSale.productSaleBatch;

import com.domain.revenue.product.batch.productBatch.ProductBatch;
import com.domain.revenue.sale.productSale.ProductSale;
import com.domain.shared.BaseEntity;
import com.domain.shared.BaseEntityCompany;
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
    public Boolean applyDiscount;

    @Column(name = "PURCHASE_PRICE", scale = 15, precision = 3, nullable = false)
    public BigDecimal purchasePrice;

    protected ProductSaleBatch() {}
}
