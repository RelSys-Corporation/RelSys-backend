package com.infrastructure;

import io.quarkus.hibernate.orm.PersistenceUnitExtension;

import io.quarkus.hibernate.orm.runtime.tenant.*;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import org.eclipse.microprofile.jwt.JsonWebToken;

@RequestScoped
@PersistenceUnitExtension
public class CustomTenantResolver implements TenantResolver {

    @Inject
    JsonWebToken jwt;


    @Override
    public String getDefaultTenantId() {
        return "0";
    }

    @Override
    public String resolveTenantId() {
        if (jwt != null && jwt.containsClaim("companyId")) {
            Object rawCompanyId = jwt.getClaim("companyId");
            if (rawCompanyId instanceof Number number)
                return String.valueOf(number.longValue());
            else if (rawCompanyId != null)
                return String.valueOf(rawCompanyId);
        }

        return getDefaultTenantId();
    }
}