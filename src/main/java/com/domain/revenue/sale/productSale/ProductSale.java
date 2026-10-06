package com.domain.revenue.sale.productSale;

import com.domain.revenue.product.Product;
import com.domain.revenue.sale.Sale;
import com.domain.shared.BaseEntity;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "PRODUCT_SALE")
public class ProductSale extends BaseEntity {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SALE_ID", nullable = false)
    public Sale sale;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PRODUCT_ID", nullable = false)
    public Product product;

    @Column(name = "QUANTITY", scale = 15, precision = 3, nullable = false)
    public BigDecimal quantity;

    @Column(name = "DISCOUNT_PERCENTAGE", scale = 5, precision = 2, nullable = false)
    public BigDecimal discountPercentage;

    @Column(name = "PRODUCT_NAME", nullable = false)
    public String productName;

    @Column(name = "PRODUCT_PRICE", scale = 15, precision = 3, nullable = false)
    public BigDecimal productPrice;

    protected ProductSale() {}
}
