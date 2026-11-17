package com.domain.financial.financialTitle;

import com.domain.financial.financialAccount.FinancialAccount;
import com.domain.financial.financialTitle.constants.FinancialTitleStatus;
import com.domain.crm.person.Person;
import com.domain.shared.BaseEntityCompany;
import com.domain.system.message.Message;
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

    @ManyToOne
    @JoinColumn(name = "FINANCIAL_TITLE_BATCH_ID", nullable = false)
    public FinancialTitleBatch batch;

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
            FinancialTitleBatch batch,
            Person person,
            FinancialTitleStatus financialTitleStatus,
            BigDecimal value,
            BigDecimal payedValue,
            LocalDateTime expirationDate,
            LocalDateTime fullPayDate
    ) {
        if (financialAccount == null)
            throw new DomainException(Message.format(Message.Type.REQUIRED_ARGUMENT, Message.Entity.FINANCIAL_ACCOUNT));

        if (financialTitleStatus == null)
            throw new DomainException(Message.format(Message.Type.REQUIRED_ARGUMENT, Message.Entity.FINANCIAL_TITLE, Message.Property.STATUS));

        if (batch == null)
            throw new DomainException(Message.format(Message.Type.REQUIRED_ARGUMENT, Message.Entity.BATCH));

        if (value == null || value.compareTo(BigDecimal.ZERO) <= 0)
            throw new DomainException(Message.format(Message.Type.SHOULD_HAVE, Message.Entity.FINANCIAL_TITLE, Message.Property.VALUE, "mairo que 0"));

        if (payedValue == null || payedValue.compareTo(BigDecimal.ZERO) < 0)
            throw new DomainException(Message.format(Message.Type.SHOULD_HAVE, Message.Entity.PAYMENT, Message.Property.VALUE, "maior ou igual a 0"));

        if (expirationDate == null)
            throw new DomainException(Message.format(Message.Type.REQUIRED_ARGUMENT, Message.Entity.EXPIRATION_DATE));

        this.financialAccount = financialAccount;
        this.installment = financialTitleInstallment;
        this.batch = batch;
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
            FinancialTitleBatch batch,
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
                batch,
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
