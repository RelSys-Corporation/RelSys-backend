package com.domain.auth.permissions;

import com.domain.shared.BaseEntity;
import com.infrastructure.exceptions.NotFoundException;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "PERMISSIONS", schema = "AUTH")
public class Permissions extends BaseEntity {
    @Column(name = "NAME", length = 100, nullable = false, unique = true)
    public String name;

    protected Permissions() {}

    public static Permissions getByIdOrThrow(Long id) {
        if (id == null)
            throw new IllegalArgumentException("O ID da permissão deve ser informado.");

        return Permissions.<Permissions>findByIdOptional(id)
                .orElseThrow(() -> new NotFoundException("Permissão (" + id + ") não encontrada."));
    }
}
