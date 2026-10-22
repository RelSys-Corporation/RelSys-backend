package com.domain.auth.authManager;


import com.domain.auth.permissions.Permissions;
import com.domain.auth.roles.Roles;
import com.domain.auth.rolesPermissions.RolesPermissions;
import io.quarkus.security.identity.AuthenticationRequestContext;
import io.quarkus.security.identity.SecurityIdentity;
import io.quarkus.security.identity.SecurityIdentityAugmentor;
import io.quarkus.security.runtime.QuarkusSecurityIdentity;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.context.control.ActivateRequestContext;

import java.util.HashSet;
import java.util.Set;

@ApplicationScoped
public class IdentityAugmentor implements SecurityIdentityAugmentor {
    @Override
    @ActivateRequestContext
    public Uni<SecurityIdentity> augment(SecurityIdentity identity, AuthenticationRequestContext context) {
        System.out.println("a:" + identity.isAnonymous());
        if (identity.isAnonymous()) {
            return Uni.createFrom().item(identity);}

        return context.runBlocking(() -> {
            Set<String> authorities = new HashSet<>(identity.getRoles());

            for (String roleName : identity.getRoles()) {
                Roles role = Roles.getByName(roleName);

                if (role != null) {
                    RolesPermissions.getPermissionByRole(role)
                            .forEach(p -> authorities.add(p.name));
                }
            }
            System.out.println(authorities);
            return QuarkusSecurityIdentity
                    .builder(identity)
                    .addRoles(authorities)
                    .build();
        });
    }
}
