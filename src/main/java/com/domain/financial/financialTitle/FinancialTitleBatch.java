package com.domain.financial.financialTitle;

import com.domain.shared.BaseEntityCompany;
import com.infrastructure.exceptions.DomainException;
import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "FINANCIAL_TITLE_BATCH", schema = "FINANCIAL")
public class FinancialTitleBatch extends BaseEntityCompany {
    @Column(name = "BATCH")
    public Long batch;

    protected FinancialTitleBatch() {}

    public static FinancialTitleBatch create() {
        FinancialTitleBatch financialTitleBatch = new FinancialTitleBatch();
        financialTitleBatch.persist();

        return financialTitleBatch;
    }
}
