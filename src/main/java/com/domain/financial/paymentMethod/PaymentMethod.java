package com.domain.financial.paymentMethod;

import com.domain.shared.BaseEntity;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "PAYMENT_METHOD")
public class PaymentMethod extends BaseEntity {
    @Column(name = "NAME", length = 50, nullable = false, unique = true)
    public String name;

    @Column(name = "ACRONYM", length = 2, nullable = false, unique = true)
    public String acronym;
}
