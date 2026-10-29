package com.domain.financial.financialTitle;

import com.domain.revenue.sale.Sale;
import com.domain.shared.BaseEntityCompany;
import com.infrastructure.exceptions.DomainException;
import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "FINANCIAL_TITLE_ORIGIN", schema = "FINANCIAL")
public class FinancialTitleOrigin extends BaseEntityCompany {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FINANCIAL_TITLE_BATCH_ID", nullable = false)
    public FinancialTitleBatch financialTitleBatch;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SALE_ID")
    public Sale sale;

    protected FinancialTitleOrigin() {}

    private FinancialTitleOrigin(
            FinancialTitleBatch financialTitleBatch,
            Sale sale
    ) {
        if (financialTitleBatch == null)
            throw new DomainException("O lote do titulo financeiro precisa ser informado.");

        this.financialTitleBatch = financialTitleBatch;
        this.sale = sale;
    }

    public static FinancialTitleOrigin create(
            FinancialTitleBatch financialTitleBatch,
            Sale sale
    ) {
        FinancialTitleOrigin financialTitleOrigin = new FinancialTitleOrigin(
                financialTitleBatch,
                sale
        );
        financialTitleOrigin.persist();

        return financialTitleOrigin;
    }
}
