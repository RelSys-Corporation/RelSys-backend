package com.domain.auth.roles;

import com.domain.shared.BaseEntity;
import com.domain.system.message.Message;
import com.domain.system.message.MessageEntity;
import com.domain.system.message.MessageProperty;
import com.infrastructure.exceptions.DomainException;
import com.infrastructure.exceptions.NotFoundException;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "ROLES", schema = "AUTH")
public class Roles extends BaseEntity {
    @Column(name = "NAME", length = 100, nullable = false, unique = true)
    public String name;

    protected Roles() {}

    private Roles(
            String name
    ) {
        if (name == null || name.isBlank())
            throw new DomainException(Message.format(Message.Type.REQUIRED_ARGUMENT, Message.Entity.ROLE, Message.Property.NAME));

        this.name = name;
    }

    public static Roles create(
            String name
    ) {
        Roles role = new Roles(name);
        role.persist();

        return role;
    }

    public static Roles getByIdOrThrow(Long id) {
        if (id == null)
            throw new DomainException("O identificar do cargo não foi informado.");

        return Roles.<Roles>findByIdOptional(id)
                .orElseThrow(() -> new NotFoundException("Cargo (" + id + ") não encontrado."));
    }

    public static Roles getByName(String name) {
        if (name == null || name.isBlank())
            return null;

        return Roles.find("name = ?1", name).firstResult();
    }
}
