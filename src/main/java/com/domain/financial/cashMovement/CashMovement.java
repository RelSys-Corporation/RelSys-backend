package com.domain.financial.cashMovement;

import com.domain.financial.cashProcess.CashProcess;
import com.domain.financial.financialAccount.FinancialAccount;
import com.domain.shared.BaseEntityCompany;
import com.infrastructure.exceptions.DomainException;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "CASH_MOVEMENT", schema = "FINANCIAL")
public class CashMovement extends BaseEntityCompany {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CASH_PROCESS_ID", nullable = false)
    public CashProcess cashProcess;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FINANCIAL_ACCOUNT_ID", nullable = false)
    public FinancialAccount financialAccount;

    @Column(name = "FLUX_ACRONYM", nullable = false)
    public String fluxAcronym;

    @Column(name = "VALUE", scale = 15, precision = 3, nullable = false)
    public BigDecimal value;

    @Column(name = "MOVEMENT_DATE",nullable = false)
    public LocalDateTime movementDate;

    protected CashMovement() {}

    private CashMovement(
            CashProcess cashProcess,
            FinancialAccount financialAccount,
            String fluxAcronym,
            BigDecimal value,
            LocalDateTime movementDate
    ) {
        if (cashProcess == null)
            throw new DomainException("O processo de caixa precisa ser informado.");

        if (financialAccount == null)
            throw new DomainException("A conta financeira precisa ser informada.");

        if (fluxAcronym == null || fluxAcronym.isBlank() || !List.of("I", "O").contains(fluxAcronym))
            throw new DomainException("O indicador de fluxo precisa ser informado ('I', 'O').");

        if (value == null || value.compareTo(BigDecimal.ZERO) <= 0)
            throw new DomainException("O valor da movimentação de caixa precisa ser superior a 0.");

        this.cashProcess = cashProcess;
        this.financialAccount = financialAccount;
        this.fluxAcronym = fluxAcronym;
        this.value = value;
        this.movementDate = (movementDate == null ? LocalDateTime.now() : movementDate);
    }

    public static CashMovement create(
            CashProcess cashProcess,
            FinancialAccount financialAccount,
            String fluxAcronym,
            BigDecimal value,
            LocalDateTime movementDate
    ) {
        CashMovement cashMovement = new CashMovement(
                cashProcess,
                financialAccount,
                fluxAcronym,
                value,
                movementDate
        );
        cashMovement.persist();

        return cashMovement;
    }
}
