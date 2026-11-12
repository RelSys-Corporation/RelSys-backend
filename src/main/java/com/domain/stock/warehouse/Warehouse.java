package com.domain.stock.warehouse;

import com.domain.shared.BaseEntityCompany;
import com.infrastructure.exceptions.DomainException;
import com.infrastructure.exceptions.NotFoundException;
import jakarta.persistence.*;

@Entity
@Table(name = "WAREHOUSE", schema = "STOCK")
public class Warehouse extends BaseEntityCompany {
    @Column(name = "NAME", length = 300, nullable = false)
    public String name;

    protected Warehouse() {}

    private Warehouse(String name) {
        if (name == null || name.isBlank())
            throw new DomainException("O nome do armazém deve ser informado.");

        this.name = name;
    }

    public static Warehouse create(String name) {
        Warehouse warehouse = new Warehouse(name);
        warehouse.persist();

        return warehouse;
    }

    public static Warehouse getByIdOrThrow(Long id) {
        if (id == null)
            throw new DomainException("O identificador do armazém deve ser informado.");

        return Warehouse.<Warehouse>findByIdOptional(id)
                .orElseThrow(() -> new NotFoundException("Armazém (" + id + ") informado não encontrado."));
    }
}