package com.domain.stock.warehouseLocation;

import com.domain.stock.warehouse.Warehouse;
import com.domain.shared.BaseEntityCompany;
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
            throw new DomainException("O armazém da localização deve ser informado.");

        if (street == null || street.isBlank())
            throw new DomainException("A rua da localização deve ser informada.");

        if (rack == null || rack.isBlank())
            throw new DomainException("A prateleira da localização deve ser informada.");

        if (level == null || level.isBlank())
            throw new DomainException("O nível da localização deve ser informado.");

        if (position == null || position.isBlank())
            throw new DomainException("A posição da localização deve ser informada.");

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
            throw new DomainException("O identificador da localização do armazém deve ser informado.");

        return WarehouseLocation.<WarehouseLocation>findByIdOptional(id)
                .orElseThrow(() -> new NotFoundException("Localização (" + id + ") informada não encontrada."));
    }
}