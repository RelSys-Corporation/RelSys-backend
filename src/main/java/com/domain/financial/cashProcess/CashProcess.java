package com.domain.financial.cashProcess;

import com.domain.financial.paymentMethod.PaymentMethod;
import com.domain.shared.BaseEntityCompany;
import com.infrastructure.exceptions.DomainException;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "CASH_PROCESS", schema = "FINANCIAL")
public class CashProcess extends BaseEntityCompany {
    @ManyToOne
    @JoinColumn(name = "PAYMENT_METHOD_ID", nullable = false)
    public PaymentMethod paymentMethod;

    @Column(name = "TOTAL_VALUE", precision = 15, scale = 3, nullable = false)
    public BigDecimal totalValue;

    @Column(name = "PROCESS_DATE")
    public LocalDateTime processDate;

    protected CashProcess() {}

    private CashProcess(
            PaymentMethod paymentMethod,
            BigDecimal totalValue,
            LocalDateTime processDate
    ) {
        if (paymentMethod == null)
            throw new DomainException("O método de pagamente precisa ser informado.");

        if (totalValue == null || totalValue.compareTo(BigDecimal.ZERO) <= 0)
            throw new DomainException("O valor do total do processo de caixa precisa ser superiro a 0.");

        this.paymentMethod = paymentMethod;
        this.totalValue = totalValue;
        this.processDate = (processDate == null ? LocalDateTime.now() : processDate);
    }

    public static CashProcess create(
            PaymentMethod paymentMethod,
            BigDecimal totalValue,
            LocalDateTime processDate
    ) {
        CashProcess cashProcess = new CashProcess(
                paymentMethod,
                totalValue,
                processDate
        );
        cashProcess.persist();

        return cashProcess;
    }
}
