package com.domain.financial.paymentMethod;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "PAYMENT_METHOD")
public class PaymentMethod extends PanacheEntityBase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    @Column(name = "NAME", length = 50, nullable = false, unique = true)
    public String name;

    @Column(name = "ACRONYM", length = 2, nullable = false, unique = true)
    public String acronym;

    @Column(name = "CREATED_AT", nullable = false)
    public LocalDateTime createdAt;
}
