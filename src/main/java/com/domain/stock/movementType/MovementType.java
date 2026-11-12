package com.domain.stock.movementType;

import com.domain.shared.BaseEntity;
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
            throw new DomainException("O nome do tipo de movimento de estoque deve ser informado.");

        if (fluxAcronym == null || fluxAcronym.isBlank())
            throw new DomainException("O sentido do fluxo do tipo de movimento deve ser informado (I - entrada, O - saída, T - transferência).");

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
            throw new DomainException("O nome do tipo de movimento de estoque deve ser informado.");

        return MovementType.<MovementType>find("name", name)
                .firstResultOptional()
                .orElseThrow(() -> new NotFoundException("Tipo de movimento de estoque (" + name + ") não encontrado."));
    }
}