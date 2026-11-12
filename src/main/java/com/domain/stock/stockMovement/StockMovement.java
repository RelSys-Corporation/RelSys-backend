package com.domain.stock.stockMovement;

import com.domain.commercial.product.Product;
import com.domain.shared.BaseEntityCompany;
import com.domain.stock.movementType.MovementType;
import com.domain.commercial.productBatch.ProductBatch;
import com.domain.stock.stockMovementOrigin.StockMovementOrigin;
import com.domain.stock.warehouse.Warehouse;
import com.domain.stock.warehouseLocation.WarehouseLocation;
import com.infrastructure.exceptions.DomainException;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "STOCK_MOVEMENT", schema = "STOCK")
public class StockMovement extends BaseEntityCompany {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MOVEMENT_TYPE_ID", nullable = false)
    public MovementType movementType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PRODUCT_ID", nullable = false)
    public Product product;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "WAREHOUSE_ID", nullable = false)
    public Warehouse warehouse;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "WAREHOUSE_LOCATION_ID", nullable = false)
    public WarehouseLocation warehouseLocation;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "STOCK_MOVEMENT_ORIGIN_ID", nullable = false)
    public StockMovementOrigin stockMovementOrigin;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PRODUCT_BATCH_ID")
    public ProductBatch productBatch;

    @Column(name = "QUANTITY", scale = 15, precision = 3, nullable = false)
    public BigDecimal quantity;

    @Column(name = "MOVEMENT_DATE", nullable = false)
    public LocalDateTime movementDate;

    protected StockMovement() {}

    private StockMovement(
            MovementType movementType,
            Product product,
            Warehouse warehouse,
            WarehouseLocation warehouseLocation,
            StockMovementOrigin stockMovementOrigin,
            ProductBatch productBatch,
            BigDecimal quantity) {
        if (movementType == null)
            throw new DomainException("O tipo do movimento de estoque deve ser informado.");

        if (product == null)
            throw new DomainException("O produto do movimento de estoque deve ser informado.");

        if (warehouse == null)
            throw new DomainException("O armazém do movimento de estoque deve ser informado.");

        if (warehouseLocation == null)
            throw new DomainException("A localização do movimento de estoque deve ser informada.");

        if (stockMovementOrigin == null)
            throw new DomainException("A origem do movimento de estoque deve ser informada.");

        if (quantity == null || quantity.compareTo(BigDecimal.ZERO) <= 0)
            throw new DomainException("A quantidade do movimento de estoque deve ser informada e ser superior a 0.");

        this.movementType = movementType;
        this.product = product;
        this.warehouse = warehouse;
        this.warehouseLocation = warehouseLocation;
        this.stockMovementOrigin = stockMovementOrigin;
        this.productBatch = productBatch;
        this.quantity = quantity;
        this.movementDate = LocalDateTime.now();
    }

    public static StockMovement create(
            MovementType movementType,
            Product product,
            Warehouse warehouse,
            WarehouseLocation warehouseLocation,
            StockMovementOrigin stockMovementOrigin,
            ProductBatch productBatch,
            BigDecimal quantity) {
        StockMovement stockMovement = new StockMovement(
                movementType,
                product,
                warehouse,
                warehouseLocation,
                stockMovementOrigin,
                productBatch,
                quantity
        );
        stockMovement.persist();

        return stockMovement;
    }
}