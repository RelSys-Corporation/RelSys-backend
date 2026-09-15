package com.domain.auth.users;

import com.domain.auth.roles.Roles;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "USERS")
public class Users extends PanacheEntityBase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    @Column(name = "NAME", length = 100, nullable = false, unique = true)
    public String name;

    @ManyToOne
    @JoinColumn(name = "ROLE_ID", nullable = false)
    public Roles role;

    @Column(name = "ACTIVE", nullable = false)
    public Boolean active;

    @Column(name = "CREATED_AT", nullable = false)
    public LocalDateTime createdAt;
}
