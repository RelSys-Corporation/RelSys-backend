package com.domain.financial.financialTitle.dtos.internal;

import com.domain.financial.financialAccount.FinancialAccount;
import com.domain.financial.financialTitle.constants.FinancialTitleStatus;
import com.domain.revenue.person.Person;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record InternalFinancialTitleDto(
        FinancialAccount financialAccount,
        BigDecimal financialTitleInstallment,
        Person person,
        FinancialTitleStatus financialTitleStatus,
        BigDecimal value,
        BigDecimal payedValue,
        LocalDateTime expirationDate,
        LocalDateTime fullPayDate
) {
}
