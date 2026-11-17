package com.domain.stock.warehouseLocation;

import com.domain.stock.warehouse.Warehouse;
import com.domain.shared.BaseEntityCompany;
import com.domain.system.message.Message;
import com.infrastructure.exceptions.DomainException;
import com.infrastructure.exceptions.NotFoundException;
import jakarta.persistence.*;

@Entity
@Table(name = "WAREHOUSE_LOCATION", schema = "STOCK")
public class WarehouseLocation extends BaseEntityCompany {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "WAREHOUSE_ID", nullable = false)
    public Warehouse warehouse;

    @Column(name = "STREET", length = 5, nullable = false)
    public String street;

    @Column(name = "RACK", length = 5, nullable = false)
    public String rack;

    @Column(name = "LEVEL", length = 5, nullable = false)
    public String level;

    @Column(name = "POSITION", length = 5, nullable = false)
    public String position;

    @Column(name = "ACTIVE", nullable = false)
    public boolean active;

    protected WarehouseLocation() {}

    private WarehouseLocation(
            Warehouse warehouse,
            String street,
            String rack,
            String level,
            String position,
            boolean active) {
        if (warehouse == null)
            throw new DomainException(Message.format(Message.Type.REQUIRED_ARGUMENT, Message.Entity.WAREHOUSE));

        if (
                (street == null || street.isBlank()) ||
                (rack == null || rack.isBlank()) ||
                (level == null || level.isBlank()) ||
                (position == null || position.isBlank())
        )
            throw new DomainException(Message.format(Message.Type.REQUIRED_ARGUMENT, Message.Entity.WAREHOUSE_LOCATION, Message.Property.DATA));

        this.warehouse = warehouse;
        this.street = street;
        this.rack = rack;
        this.level = level;
        this.position = position;
        this.active = active;
    }

    public static WarehouseLocation create(
            Warehouse warehouse,
            String street,
            String rack,
            String level,
            String position,
            boolean active) {
        WarehouseLocation warehouseLocation = new WarehouseLocation(
                warehouse,
                street,
                rack,
                level,
                position,
                active
        );
        warehouseLocation.persist();

        return warehouseLocation;
    }

    public static WarehouseLocation getByIdOrThrow(Long id) {
        if (id == null)
            throw new DomainException(Message.format(Message.Type.IDENTIFIER_ABSENT, Message.Entity.WAREHOUSE_LOCATION));

        return WarehouseLocation.<WarehouseLocation>findByIdOptional(id)
                .orElseThrow(() -> new NotFoundException(Message.format(Message.Type.NOT_FOUND, Message.Entity.WAREHOUSE_LOCATION, id)));
    }
}