package com.domain.financial.financialTitle.dtos.internal;

import com.domain.financial.financialTitle.FinancialTitle;
import com.domain.financial.financialTitle.FinancialTitleBatch;

import java.util.List;

public record InternalFinancialTitleResultDto(
        List<FinancialTitle> financialTitle,
        FinancialTitleBatch batch
) {
}
