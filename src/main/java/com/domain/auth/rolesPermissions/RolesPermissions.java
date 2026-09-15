package com.domain.auth.rolesPermissions;

import com.domain.auth.permissions.Permissions;
import com.domain.auth.roles.Roles;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "ROLES_PERMISSIONS")
public class RolesPermissions extends PanacheEntityBase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    @ManyToOne
    @JoinColumn(name = "ROLE_ID", nullable = false)
    public Roles role;

    @ManyToOne
    @JoinColumn(name = "PERMISSION_ID", nullable = false)
    public Permissions permission;

    @Column(name = "CREATED_AT", nullable = false)
    public LocalDateTime createdAt;
}
