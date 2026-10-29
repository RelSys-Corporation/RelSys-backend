package com.domain.financial.cashProcess;

import com.domain.financial.financialTitle.FinancialTitle;
import com.domain.shared.BaseEntityCompany;
import com.infrastructure.exceptions.DomainException;
import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "CASH_PROCESS_ITEM", schema = "FINANCIAL")
public class CashProcessItem extends BaseEntityCompany {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CASH_PROCESS_ID", nullable = false)
    public CashProcess cashProcess;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FINANCIAL_TITLE_ID", nullable = false)
    public FinancialTitle financialTitle;

    @Column(name = "FINANCIAL_TITLE_INSTALLMENT", scale = 5)
    public BigDecimal financialTitleInstallment;

    @Column(name = "PAID_VALUE", scale = 15, precision = 3, nullable = false)
    public BigDecimal paidValue;

    @Column(name = "DISCOUNT_VALUE", scale = 15, precision = 3, nullable = false)
    public BigDecimal discountValue;

    @Column(name = "INTEREST_VALUE", scale = 15, precision = 3, nullable = false)
    public BigDecimal interestValue;

    protected CashProcessItem() {}

    private CashProcessItem(
            CashProcess cashProcess,
            FinancialTitle financialTitle,
            BigDecimal financialTitleInstallment,
            BigDecimal paidValue,
            BigDecimal discountValue,
            BigDecimal interestValue
    ) {
        if (cashProcess == null)
            throw new DomainException("O processo de caixa precisa ser informado.");

        if (financialTitle == null)
            throw new DomainException("O titulo financeiro precisa ser informado.");

        if (paidValue == null || paidValue.compareTo(BigDecimal.ZERO) <= 0)
            throw new DomainException("O valor pago precisa ser superior a 0.");

        if (discountValue == null || discountValue.compareTo(BigDecimal.ZERO) < 0)
            throw new DomainException("O valor de desconto precisa ser igual ou superior a 0.");

        if (interestValue == null || interestValue.compareTo(BigDecimal.ZERO) < 0)
            throw new DomainException("O valor de juros precisa ser igual ou superior a 0.");

        if (paidValue.add(interestValue).compareTo(discountValue) < 0)
            throw new DomainException("O valor do desconto é maior que o valor pago.");

        this.cashProcess = cashProcess;
        this.financialTitle = financialTitle;
        this.financialTitleInstallment = financialTitleInstallment;
        this.paidValue = paidValue;
        this.discountValue = discountValue;
        this.interestValue = interestValue;
    }

    public static CashProcessItem create(
            CashProcess cashProcess,
            FinancialTitle financialTitle,
            BigDecimal financialTitleInstallment,
            BigDecimal paidValue,
            BigDecimal discountValue,
            BigDecimal interestValue
    ) {
        CashProcessItem cashProcessItem = new CashProcessItem(
                cashProcess,
                financialTitle,
                financialTitleInstallment,
                paidValue,
                discountValue,
                interestValue
        );
        cashProcessItem.persist();

        return cashProcessItem;
    }
}
