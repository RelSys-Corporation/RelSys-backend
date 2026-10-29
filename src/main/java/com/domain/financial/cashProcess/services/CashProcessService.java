package com.domain.financial.cashProcess.services;

import com.domain.financial.cashProcess.CashProcess;
import com.domain.financial.cashProcess.CashProcessItem;
import com.domain.financial.cashProcess.dtos.internal.InternalCashProcessItemDto;
import com.domain.financial.paymentMethod.PaymentMethod;
import jakarta.enterprise.context.ApplicationScoped;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@ApplicationScoped
public class CashProcessService {
    public CashProcess createCashProcess(
            PaymentMethod paymentMethod,
            LocalDateTime processDate,
            List<InternalCashProcessItemDto> cashProcessItemDtos
    ) {
        BigDecimal totalValue = cashProcessItemDtos.stream()
                .map(dto -> dto.paidValue()
                        .add(dto.interestValue())
                        .subtract(dto.discountValue())
                ).reduce(BigDecimal.ZERO, BigDecimal::add);

        CashProcess cashProcess = CashProcess.create(
                paymentMethod,
                totalValue,
                processDate
        );

        for (InternalCashProcessItemDto dto : cashProcessItemDtos) {
            CashProcessItem.create(
                    cashProcess,
                    dto.financialTitle(),
                    dto.financialTitleInstallment(),
                    dto.paidValue(),
                    dto.discountValue(),
                    dto.interestValue()
            );
        }

        return cashProcess;
    }
}