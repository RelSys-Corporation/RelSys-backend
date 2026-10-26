package com.domain.system.company;

import com.domain.shared.BaseEntity;
import com.infrastructure.exceptions.DomainException;
import com.infrastructure.exceptions.NotFoundException;
import jakarta.persistence.*;

@Entity
@Table(name = "COMPANY", schema = "SYSTEM")
public class Company extends BaseEntity {
    @Column(name = "CORPORATE_NAME", length = 200, nullable = false, unique = true)
    public String corporateName;

    @Column(name = "TRADE_NAME", length = 200)
    public String tradeName;

    @Column(name = "IS_HEADQUARTER", nullable = false)
    public boolean isHeadquarter;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "HEADQUARTER_COMPANY_ID")
    public Company headquarterCompany;

    protected Company() {}

    private Company(
            String corporateName,
            String tradeName,
            boolean isHeadquarter,
            Company headquarterCompany
    ) {
        if (corporateName == null || corporateName.isBlank())
            throw new DomainException("O nome corporativo da empresa deve ser informado.");

        if (isHeadquarter)
            headquarterCompany = null;

        this.corporateName = corporateName;
        this.tradeName = tradeName;
        this.isHeadquarter = isHeadquarter;
        this.headquarterCompany = headquarterCompany;
    }

    public static Company getById(Long id) {
        if (id == null)
            return null;

        return Company.findById(id);
    }

    public static Company getByIdOrThrow(Long id) {
        if (id == null)
            return null;

        return Company.<Company>findByIdOptional(id)
                .orElseThrow(() -> new NotFoundException("Empresa (" + id + ") não encontrada."));
    }

    public static Company create(
            String corporateName,
            String tradeName,
            boolean isHeadquarter,
            Company headquarterCompany
    ) {
        Company company = new Company(
                corporateName,
                tradeName,
                isHeadquarter,
                headquarterCompany
        );
        company.persist();

        return company;
    }
}
