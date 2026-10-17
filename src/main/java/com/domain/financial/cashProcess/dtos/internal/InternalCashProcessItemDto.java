package com.domain.financial.cashProcess.dtos.internal;

import com.domain.financial.financialTitle.FinancialTitle;

import java.math.BigDecimal;

public record InternalCashProcessItemDto(
        FinancialTitle financialTitle,
        BigDecimal financialTitleInstallment,
        BigDecimal paidValue,
        BigDecimal discountValue,
        BigDecimal interestValue
) {
}
