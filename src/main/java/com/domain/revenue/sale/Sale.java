package com.domain.revenue.sale;

import com.domain.financial.paymentMethod.PaymentMethod;
import com.domain.revenue.client.Client;
import com.domain.revenue.sale.enums.saleStatus.SaleStatus;
import com.domain.shared.BaseEntity;
import com.domain.shared.BaseEntityCompany;
import com.infrastructure.exceptions.DomainException;
import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "SALE", schema = "REVENUE")
public class Sale extends BaseEntityCompany {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CLIENT_ID")
    public Client client;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PAYMENT_METHOD_ID", nullable = false)
    public PaymentMethod paymentMethod;

    @Column(name = "DISCOUNT_PERCENTAGE", scale = 5, precision = 2, nullable = false)
    public BigDecimal discountPercentage;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SALE_STATUS_ID", nullable = false)
    public SaleStatus saleStatus;

    protected Sale() {}

    private Sale(
            Client client,
            PaymentMethod paymentMethod,
            BigDecimal discountPercentage,
            SaleStatus saleStatus
    ) {
        if (paymentMethod == null)
            throw new DomainException("O método de pagamento deve ser informado.");

        if (saleStatus == null)
            throw new DomainException("O status da venda deve ser informado.");

        this.client = client;
        this.paymentMethod = paymentMethod;
        this.discountPercentage = (discountPercentage == null ? BigDecimal.ZERO : discountPercentage);
        this.saleStatus = saleStatus;
    }

    public static Sale create(
            Client client,
            PaymentMethod paymentMethod,
            BigDecimal discountPercentage,
            SaleStatus saleStatus
    ) {
        Sale sale = new Sale(
                client,
                paymentMethod,
                discountPercentage,
                saleStatus
        );
        sale.persist();

        return sale;
    }
}
