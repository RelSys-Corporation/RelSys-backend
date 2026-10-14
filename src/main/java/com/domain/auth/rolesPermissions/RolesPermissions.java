package com.domain.auth.rolesPermissions;

import com.domain.auth.permissions.Permissions;
import com.domain.auth.roles.Roles;
import com.domain.shared.BaseEntity;
import com.infrastructure.exceptions.DomainException;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

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

    private RolesPermissions(
            Roles role,
            Permissions permission
    ) {
        if (role == null)
            throw new DomainException("O cargo deve ser informado.");

        if (permission == null)
            throw new DomainException("A permissão deve ser informada.");

        this.role = role;
        this.permission = permission;
    }

    public static RolesPermissions create(
            Roles role,
            Permissions permission
    ) {
        RolesPermissions rolesPermissions = new RolesPermissions(
                role,
                permission
        );
        rolesPermissions.persist();

        return rolesPermissions;
    }

    public static List<Permissions> getPermissionByRole(Roles role) {
        return Permissions.<Permissions>find(
                        """
                        select rp.permission
                          from RolesPermissions rp
                         where rp.role.id = ?1
                        """,
                role.id
        ).list();
    }
}
