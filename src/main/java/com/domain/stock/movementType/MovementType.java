package com.domain.stock.movementType;

import com.domain.shared.BaseEntity;
import com.domain.system.message.Message;
import com.infrastructure.exceptions.DomainException;
import com.infrastructure.exceptions.NotFoundException;
import jakarta.persistence.*;

@Entity
@Table(name = "MOVEMENT_TYPE", schema = "STOCK")
public class MovementType extends BaseEntity {
    @Column(name = "NAME", length = 100, nullable = false, unique = true)
    public String name;

    @Column(name = "FLUX_ACRONYM", length = 1, nullable = false)
    public String fluxAcronym;

    protected MovementType() {}

    private MovementType(String name, String fluxAcronym) {
        if (name == null || name.isBlank())
            throw new DomainException(Message.format(Message.Type.REQUIRED_ARGUMENT, Message.Entity.STOCK_MOVEMENT, Message.Property.TYPE));

        if (fluxAcronym == null || fluxAcronym.isBlank())
            throw new DomainException(Message.format(Message.Type.REQUIRED_ARGUMENT, Message.Entity.FLUX, Message.Property.DIRECTION));

        this.name = name;
        this.fluxAcronym = fluxAcronym;
    }

    public static MovementType create(String name, String fluxAcronym) {
        MovementType movementType = new MovementType(name, fluxAcronym);
        movementType.persist();

        return movementType;
    }

    public static MovementType getByName(String name) {
        if (name == null || name.isBlank())
            throw new DomainException(Message.format(Message.Type.IDENTIFIER_ABSENT, Message.Entity.STOCK_MOVEMENT, Message.Property.TYPE));

        return MovementType.<MovementType>find("name", name)
                .firstResultOptional()
                .orElseThrow(() -> new NotFoundException(Message.format(Message.Type.NOT_FOUND, Message.Entity.STOCK_MOVEMENT, Message.Property.TYPE, name)));
    }
}