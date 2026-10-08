package com.domain.auth.rolesPermissions;

import com.domain.auth.permissions.Permissions;
import com.domain.auth.roles.Roles;
import com.domain.shared.BaseEntity;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "ROLES_PERMISSIONS", schema = "AUTH")
public class RolesPermissions extends BaseEntity {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ROLE_ID", nullable = false)
    public Roles role;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PERMISSION_ID", nullable = false)
    public Permissions permission;

    protected RolesPermissions() {}
}
