package com.domain.financial.financialTitle;

import com.domain.shared.BaseEntityCompany;
import com.infrastructure.exceptions.DomainException;
import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "FINANCIAL_TITLE_BATCH", schema = "FINANCIAL")
public class FinancialTitleBatch extends BaseEntityCompany {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FINANCIAL_TITLE_ID", nullable = false)
    public FinancialTitle financialTitle;

    @Column(name = "FINANCIAL_TITLE_INSTALLMENT")
    public BigDecimal financialTitleInstallment;

    @Column(name = "BATCH")
    public BigDecimal batch;

    protected FinancialTitleBatch() {}

    private FinancialTitleBatch(
            FinancialTitle financialTitle,
            BigDecimal financialTitleInstallment,
            BigDecimal batch
    ) {
        if (financialTitle == null)
            throw new DomainException("O titulo precisa ser informado para geração do lote.");

        this.financialTitle = financialTitle;
        this.financialTitleInstallment = financialTitleInstallment;
        this.batch = batch;
    }

    public static FinancialTitleBatch create(
            FinancialTitle financialTitle,
            BigDecimal financialTitleInstallment,
            BigDecimal batch
    ) {
        FinancialTitleBatch financialTitleBatch = new FinancialTitleBatch(
                financialTitle,
                financialTitleInstallment,
                batch
        );
        financialTitleBatch.persist();

        return financialTitleBatch;
    }
}
