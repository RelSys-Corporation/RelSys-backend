package com.domain.auth.users;

import com.domain.auth.roles.Roles;
import com.domain.shared.BaseEntity;
import com.infrastructure.exceptions.NotFoundException;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.*;
import jakarta.ws.rs.Consumes;

import java.time.LocalDateTime;

@Entity
@Table(name = "USERS", schema = "AUTH")
public class Users extends BaseEntity {
    @Column(name = "NAME", length = 100, nullable = false, unique = true)
    public String name;

    @Column(name = "PASSWORD", length = 500, nullable = false)
    public String password;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ROLE_ID", nullable = false)
    public Roles role;

    @Column(name = "ACTIVE", nullable = false)
    public Boolean active;

    protected Users() {}

    private Users(
            String name,
            String password) {
        this.name = name;
        this.password = password;
        this.active = Boolean.TRUE;
    }

    public static Users create(
            String name,
            String password
    ) {
        Users user = new Users(
                name,
                password
        );
        user.persist();

        return user;
    }

    public static Users getByIdOrThrow(Long id) {
        return Users.<Users>findByIdOptional(id)
                .orElseThrow(() -> new NotFoundException("Usuário (" + id + ") não encontrado."));
    }

    public static Users getByNameOrThrow(String name) {
        return Users.<Users>find(
                "name = ?1",
                name)
                .firstResultOptional()
                .orElseThrow(() -> new NotFoundException("Usuário (" + name + ") não encontrado."));
    }
}
