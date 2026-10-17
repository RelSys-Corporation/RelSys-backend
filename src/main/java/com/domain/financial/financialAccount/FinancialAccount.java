package com.domain.financial.financialAccount;

import com.domain.shared.BaseEntityCompany;
import com.infrastructure.exceptions.DomainException;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import java.util.List;

@Entity
@Table(name = "FINANCIAL_ACCOUNT", schema = "FINANCIAL")
public class FinancialAccount extends BaseEntityCompany {
    @Column(name = "NAME", nullable = false)
    public String name;

    @Column(name = "FLUX_ACRONYM", nullable = false)
    public String fluxAcronym;

    protected FinancialAccount() {}

    private FinancialAccount(
            String name,
            String fluxAcronym
    ) {
        if (name == null || name.isBlank())
            throw new DomainException("O nome da conta precisa ser informado.");

        if (fluxAcronym == null || fluxAcronym.isBlank() || !List.of("R", "P").contains(fluxAcronym))
            throw new DomainException("O indicador da conta precisa ser informado ('R', 'P').");

        this.name = name;
        this.fluxAcronym = fluxAcronym;
    }

    public static FinancialAccount create(
            String name,
            String fluxAcronym
    ) {
        FinancialAccount financialAccount = new FinancialAccount(
                name,
                fluxAcronym
        );
        financialAccount.persist();

        return financialAccount;
    }
}
