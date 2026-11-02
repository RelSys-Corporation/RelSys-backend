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

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@ApplicationScoped
public class FinancialTitleService {
    public InternalFinancialTitleResultDto createFinancialTitle(
            List<InternalFinancialTitleDto> financialTitleDtos,
            InternalFinancialTitleOriginDto originDto
    ) {
        List<FinancialTitle> titles = new ArrayList<>();
        FinancialTitleBatch batch = FinancialTitleBatch.create();

        for (InternalFinancialTitleDto dto : financialTitleDtos) {
            titles.add(
                    FinancialTitle.create(
                            dto.financialAccount(),
                            dto.financialTitleInstallment(),
                            batch,
                            dto.person(),
                            dto.financialTitleStatus(),
                            dto.value(),
                            dto.payedValue(),
                            dto.expirationDate(),
                            dto.fullPayDate()
                    ));
        }


        FinancialTitleOrigin.create(
                batch,
                originDto.sale()
        );

        return new InternalFinancialTitleResultDto(titles, batch);
    }
}