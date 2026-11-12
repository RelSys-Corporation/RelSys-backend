package com.domain.stock.productSaleBatch;

import com.domain.commercial.productBatch.ProductBatch;
import com.domain.commercial.sale.productSale.ProductSale;
import com.domain.shared.BaseEntityCompany;
import com.infrastructure.exceptions.DomainException;
import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "PRODUCT_SALE_BATCH", schema = "STOCK")
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
            BigDecimal quantity
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
        this.applyDiscount = productBatch.productSupplier.discountAllowed;
        this.purchasePrice = productBatch.purchasePrice;
    }

    public static ProductSaleBatch create(
            ProductSale productSale,
            ProductBatch productBatch,
            BigDecimal quantity
    ) {
        ProductSaleBatch productSaleBatch = new ProductSaleBatch(
                productSale,
                productBatch,
                quantity
        );
        productSaleBatch.persist();

        return productSaleBatch;
    }
}
