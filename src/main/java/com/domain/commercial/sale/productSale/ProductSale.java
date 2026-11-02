package com.domain.revenue.sale.productSale;

import com.domain.revenue.product.Product;
import com.domain.revenue.sale.Sale;
import com.domain.shared.BaseEntity;
import com.domain.shared.BaseEntityCompany;
import com.infrastructure.exceptions.DomainException;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "PRODUCT_SALE", schema = "REVENUE")
public class ProductSale extends BaseEntityCompany {
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

    private ProductSale(
            Sale sale,
            Product product,
            BigDecimal quantity,
            BigDecimal discountPercentage
    ) {
        if (sale == null)
            throw new DomainException("Deve ser informada uma venda pra o produto da venda.");

        if (product == null)
            throw new DomainException("Deve ser informado o produto da venda.");

        if (quantity == null || quantity.compareTo(BigDecimal.ZERO) <= 0)
            throw new DomainException("A quantidade da venda deve ser informada e ser superior a 0.");

        this.sale = sale;
        this.product = product;
        this.quantity = quantity;
        this.discountPercentage = (discountPercentage == null ? BigDecimal.ZERO : discountPercentage);
        this.productName = product.name;
        this.productPrice = product.price;
    }

    public static ProductSale create(
            Sale sale,
            Product product,
            BigDecimal quantity,
            BigDecimal discountPercentage
    ) {
        ProductSale productSale = new ProductSale(
                sale,
                product,
                quantity,
                discountPercentage
        );
        productSale.persist();

        return productSale;
    }
}
