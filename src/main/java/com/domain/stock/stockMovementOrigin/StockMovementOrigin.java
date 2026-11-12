package com.domain.stock.stockMovementOrigin;

import com.domain.commercial.sale.Sale;
import com.domain.shared.BaseEntityCompany;
import com.domain.commercial.productBatch.ProductBatch;
import com.infrastructure.exceptions.DomainException;
import jakarta.persistence.*;

@Entity
@Table(name = "STOCK_MOVEMENT_ORIGIN", schema = "STOCK")
public class StockMovementOrigin extends BaseEntityCompany {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SALE_ID")
    public Sale sale;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PRODUCT_BATCH_ID")
    public ProductBatch productBatch;

    protected StockMovementOrigin() {}

    private StockMovementOrigin(Sale sale, ProductBatch productBatch) {
        boolean saleOrigin = sale != null;
        boolean batchOrigin = productBatch != null;

        if (saleOrigin == batchOrigin)
            throw new DomainException("A origem do movimento de estoque deve referenciar uma venda ou um lote de produto.");

        this.sale = sale;
        this.productBatch = productBatch;
    }

    public static StockMovementOrigin createForSale(Sale sale) {
        StockMovementOrigin stockMovementOrigin = new StockMovementOrigin(sale, null);
        stockMovementOrigin.persist();

        return stockMovementOrigin;
    }

    public static StockMovementOrigin createForProductBatch(ProductBatch productBatch) {
        StockMovementOrigin stockMovementOrigin = new StockMovementOrigin(null, productBatch);
        stockMovementOrigin.persist();

        return stockMovementOrigin;
    }
}