package com.domain.financial.financialTitle.dtos.internal;

import com.domain.financial.financialTitle.FinancialTitle;
import com.domain.financial.financialTitle.FinancialTitleBatch;

public record InternalFinancialTitleResultDto(
        FinancialTitle financialTitle,
        FinancialTitleBatch batch
) {
}
