package com.domain.financial.financialTitle.services;

import com.domain.financial.financialAccount.FinancialAccount;
import com.domain.financial.financialTitle.FinancialTitle;
import com.domain.financial.financialTitle.FinancialTitleBatch;
import com.domain.financial.financialTitle.FinancialTitleOrigin;
import com.domain.financial.financialTitle.constants.FinancialTitleStatus;
import com.domain.financial.financialTitle.dtos.internal.InternalFinancialTitleDto;
import com.domain.financial.financialTitle.dtos.internal.InternalFinancialTitleOriginDto;
import com.domain.financial.financialTitle.dtos.internal.InternalFinancialTitleResultDto;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.Map;

@ApplicationScoped
public class FinancialTitleService {
    public InternalFinancialTitleResultDto createFinancialTitle(
            InternalFinancialTitleDto financialTitleDto,
            FinancialTitleBatch financialTitleBatch,
            InternalFinancialTitleOriginDto originDto
    ) {
        FinancialTitle financialTitle = FinancialTitle.create(
                financialTitleDto.financialAccount(),
                financialTitleDto.financialTitleInstallment(),
                financialTitleDto.person(),
                financialTitleDto.financialTitleStatus(),
                financialTitleDto.value(),
                financialTitleDto.payedValue(),
                financialTitleDto.expirationDate(),
                financialTitleDto.fullPayDate()
        );

        FinancialTitleBatch batch = FinancialTitleBatch.create(
                financialTitle,
                financialTitleDto.financialTitleInstallment(),
                financialTitleBatch.batch
        );

        FinancialTitleOrigin.create(
                batch,
                originDto.sale()
        );

        return new InternalFinancialTitleResultDto(financialTitle, batch);
    }
}