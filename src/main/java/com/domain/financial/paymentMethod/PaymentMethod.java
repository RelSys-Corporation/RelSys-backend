package com.domain.financial.paymentMethod;

import com.domain.shared.BaseEntityCompany;
import jakarta.persistence.*;

@Entity
@Table(name = "PAYMENT_METHOD")
public class PaymentMethod extends BaseEntityCompany {
    @Column(name = "NAME", length = 50, nullable = false, unique = true)
    public String name;

    @Column(name = "ACRONYM", length = 2, nullable = false, unique = true)
    public String acronym;

    protected PaymentMethod() {};
}
