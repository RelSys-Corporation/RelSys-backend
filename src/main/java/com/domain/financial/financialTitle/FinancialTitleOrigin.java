package com.domain.financial.financialTitle;

import com.domain.revenue.sale.Sale;
import com.domain.shared.BaseEntityCompany;
import com.infrastructure.exceptions.DomainException;
import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "FINANCIAL_TITLE_ORIGIN", schema = "FINANCIAL")
public class FinancialTitleOrigin extends BaseEntityCompany {
    @ManyToOne
    @JoinColumn(name = "FINANCIAL_TITLE", nullable = false)
    public FinancialTitle financialTitle;

    @Column(name = "FINANCIAL_TITLE_INSTALLMENT")
    public BigDecimal financialTitleInstallment;

    @ManyToOne
    @JoinColumn(name = "SALE_ID")
    public Sale sale;

    protected FinancialTitleOrigin() {}

    private FinancialTitleOrigin(
            FinancialTitle financialTitle,
            BigDecimal financialTitleInstallment,
            Sale sale
    ) {
        if (financialTitle == null)
            throw new DomainException("O titulo financeiro precisa ser informado.");

        this.financialTitle = financialTitle;
        this.financialTitleInstallment = financialTitleInstallment;
        this.sale = sale;
    }

    public static FinancialTitleOrigin create(
            FinancialTitle financialTitle,
            BigDecimal financialTitleInstallment,
            Sale sale
    ) {
        FinancialTitleOrigin financialTitleOrigin = new FinancialTitleOrigin(
                financialTitle,
                financialTitleInstallment,
                sale
        );
        financialTitleOrigin.persist();

        return financialTitleOrigin;
    }
}
