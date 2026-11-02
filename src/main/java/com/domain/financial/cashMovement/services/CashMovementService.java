package com.domain.financial.cashMovement.services;

import com.domain.financial.cashMovement.CashMovement;
import com.domain.financial.cashProcess.CashProcess;
import com.domain.financial.financialAccount.FinancialAccount;
import jakarta.enterprise.context.ApplicationScoped;

import java.time.LocalDateTime;

@ApplicationScoped
public class CashMovementService {
    public CashMovement createCashMovement(
            CashProcess cashProcess,
            FinancialAccount financialAccount
    ) {
        return CashMovement.create(
                cashProcess,
                financialAccount,
                (financialAccount.fluxAcronym.equals("R") ? "I" : "O"),
                cashProcess.totalValue,
                LocalDateTime.now()
        );
    }
}
