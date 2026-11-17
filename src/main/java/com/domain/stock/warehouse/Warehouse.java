package com.domain.stock.warehouse;

import com.domain.shared.BaseEntityCompany;
import com.domain.system.message.Message;
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
            throw new DomainException(Message.format(Message.Type.REQUIRED_ARGUMENT, Message.Entity.WAREHOUSE, Message.Property.NAME));

        this.name = name;
    }

    public static Warehouse create(String name) {
        Warehouse warehouse = new Warehouse(name);
        warehouse.persist();

        return warehouse;
    }

    public static Warehouse getByIdOrThrow(Long id) {
        if (id == null)
            throw new DomainException(Message.format(Message.Type.IDENTIFIER_ABSENT, Message.Entity.WAREHOUSE));

        return Warehouse.<Warehouse>findByIdOptional(id)
                .orElseThrow(() -> new NotFoundException(Message.format(Message.Type.NOT_FOUND, Message.Entity.WAREHOUSE, id)));
    }
}