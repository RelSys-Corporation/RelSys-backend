package com.domain.financial.services;

import com.domain.financial.cashMovement.CashMovement;
import com.domain.financial.cashMovement.services.CashMovementService;
import com.domain.financial.cashProcess.CashProcess;
import com.domain.financial.cashProcess.dtos.internal.InternalCashProcessItemDto;
import com.domain.financial.cashProcess.services.CashProcessService;
import com.domain.financial.financialAccount.FinancialAccount;
import com.domain.financial.financialTitle.FinancialTitle;
import com.domain.financial.financialTitle.FinancialTitleOrigin;
import com.domain.financial.financialTitle.constants.FinancialTitleStatus;
import com.domain.financial.financialTitle.dtos.internal.InternalFinancialTitleDto;
import com.domain.financial.financialTitle.dtos.internal.InternalFinancialTitleOriginDto;
import com.domain.financial.financialTitle.dtos.internal.InternalFinancialTitleResultDto;
import com.domain.financial.financialTitle.services.FinancialTitleService;
import com.domain.financial.paymentMethod.PaymentMethod;
import com.domain.revenue.person.Person;
import jakarta.inject.Inject;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public class FinancialService {
    @Inject
    FinancialTitleService financialTitleService;

    @Inject
    CashProcessService cashProcessService;

    @Inject
    CashMovementService cashMovementService;

    public CashMovement simpleSaleFinancialProcess(
            FinancialAccount financialAccount,
            FinancialAccount receiveAccount,
            Person person,
            BigDecimal value,
            InternalFinancialTitleOriginDto originDto,
            PaymentMethod paymentMethod
    ) {
        List<InternalFinancialTitleDto> titleDto = List.of(new InternalFinancialTitleDto(
                financialAccount,
                BigDecimal.ZERO,
                person,
                FinancialTitleStatus.of(FinancialTitleStatus.Type.PENDING),
                value,
                BigDecimal.ZERO,
                LocalDateTime.now().plusHours(3),
                null
        ));

        InternalFinancialTitleResultDto financialTitleDto =  financialTitleService.createFinancialTitle(
                titleDto,
                originDto
        );

        InternalCashProcessItemDto itemDto = new InternalCashProcessItemDto(
                financialTitleDto.financialTitle().getFirst(),
                BigDecimal.ZERO,
                value,
                BigDecimal.ZERO,
                BigDecimal.ZERO
        );

        CashProcess cashProcess = cashProcessService.createCashProcess(
                paymentMethod,
                List.of(itemDto)
        );

        return cashMovementService.createCashMovement(
                cashProcess,
                receiveAccount
        );
    }
}
