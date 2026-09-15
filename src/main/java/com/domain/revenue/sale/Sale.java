package com.domain.revenue.sale;

import com.domain.financial.paymentMethod.PaymentMethod;
import com.domain.revenue.client.Client;
import com.domain.revenue.sale.enums.saleStatus.SaleStatus;
import com.domain.shared.BaseEntity;
import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "SALE")
public class Sale extends BaseEntity {
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
}
