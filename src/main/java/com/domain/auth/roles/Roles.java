package com.domain.auth.roles;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "ROLES")
public class Roles extends PanacheEntityBase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    @Column(name = "NAME", length = 100, nullable = false, unique = true)
    public String name;

    @Column(name = "CREATED_AT", nullable = false)
    public LocalDateTime createdAt;
}
