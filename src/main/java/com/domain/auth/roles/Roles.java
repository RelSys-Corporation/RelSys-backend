package com.domain.auth.roles;

import com.domain.shared.BaseEntity;
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

    public static Roles getByIdOrThrow(Long id) {
        return Roles.<Roles>findByIdOptional(id)
                .orElseThrow(() -> new NotFoundException("Role (" + id + ") não encontrada."));
    }
}
