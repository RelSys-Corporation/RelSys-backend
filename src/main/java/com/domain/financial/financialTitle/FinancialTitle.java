package com.domain.financial.financialTitle;

import com.domain.financial.financialAccount.FinancialAccount;
import com.domain.financial.financialTitle.constants.FinancialTitleStatus;
import com.domain.revenue.person.Person;
import com.domain.shared.BaseEntityCompany;
import com.infrastructure.exceptions.DomainException;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "FINANCIAL_TITLE", schema = "FINANCIAL")
public class FinancialTitle extends BaseEntityCompany {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FINANCIAL_ACCOUNT_ID", nullable = false)
    public FinancialAccount financialAccount;

    @Column(name = "INSTALLMENT", precision = 5)
    public BigDecimal installment;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PERSON_ID")
    public Person person;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FINANCIAL_TITLE_STATUS_ID")
    public FinancialTitleStatus financialTitleStatus;

    @Column(name = "VALUE", scale = 15, precision = 3, nullable = false)
    public BigDecimal value;

    @Column(name = "PAYED_VALUE", scale = 15, precision = 3, nullable = false)
    public BigDecimal payedValue;

    @Column(name = "EXPIRATION_DATE", nullable = false)
    public LocalDateTime expirationDate;

    @Column(name = "FULL_PAY_DATE")
    public LocalDateTime fullPayDate;

    protected FinancialTitle() {}

    private FinancialTitle(
            FinancialAccount financialAccount,
            BigDecimal financialTitleInstallment,
            Person person,
            FinancialTitleStatus financialTitleStatus,
            BigDecimal value,
            BigDecimal payedValue,
            LocalDateTime expirationDate,
            LocalDateTime fullPayDate
    ) {
        if (financialAccount == null)
            throw new DomainException("A conta financeira precisa ser informada.");

        if (financialTitleStatus == null)
            throw new DomainException("O status do titulo preicsa ser informado.");

        if (value == null || value.compareTo(BigDecimal.ZERO) <= 0)
            throw new DomainException("O valor do titulo precisa ser maior do que 0.");

        if (payedValue == null || payedValue.compareTo(BigDecimal.ZERO) < 0)
            throw new DomainException("O valor pago não pode ser inferior a 0.");

        if (expirationDate == null)
            throw new DomainException("A data de vencimento precisa ser informada.");

        this.financialAccount = financialAccount;
        this.installment = financialTitleInstallment;
        this.person = person;
        this.financialTitleStatus = financialTitleStatus;
        this.value = value;
        this.payedValue = payedValue;
        this.expirationDate = expirationDate;
        this.fullPayDate = fullPayDate;
    }

    public static FinancialTitle create(
            FinancialAccount financialAccount,
            BigDecimal financialTitleInstallment,
            Person person,
            FinancialTitleStatus financialTitleStatus,
            BigDecimal value,
            BigDecimal payedValue,
            LocalDateTime expirationDate,
            LocalDateTime fullPayDate
    ) {
        FinancialTitle financialTitle = new FinancialTitle(
                financialAccount,
                financialTitleInstallment,
                person,
                financialTitleStatus,
                value,
                payedValue,
                expirationDate,
                fullPayDate
        );
        financialTitle.persist();

        return financialTitle;
    }
}
