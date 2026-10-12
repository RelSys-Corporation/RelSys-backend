package com.domain.system.company.services;

import com.domain.system.company.Company;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.validation.constraints.NotNull;

@ApplicationScoped
public class CompanyService {
    public Company createCompany(
            String corporateName,
            String tradeName,
            boolean isHeadquarter,
            Long companyId
    ) {
        Company headquarterCompany = Company.getById(companyId);

        return Company.create(
                corporateName,
                tradeName,
                isHeadquarter,
                headquarterCompany
        );
    }
}
