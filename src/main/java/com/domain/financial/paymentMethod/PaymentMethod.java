package com.domain.financial.paymentMethod;

import com.domain.shared.BaseEntityCompany;
import com.infrastructure.exceptions.NotFoundException;
import jakarta.persistence.*;

@Entity
@Table(name = "PAYMENT_METHOD", schema = "FINANCIAL")
public class PaymentMethod extends BaseEntityCompany {
    @Column(name = "NAME", length = 50, nullable = false, unique = true)
    public String name;

    @Column(name = "ACRONYM", length = 2, nullable = false, unique = true)
    public String acronym;

    protected PaymentMethod() {};

    public static PaymentMethod getByIdOrThrow(Long id) {
        if (id == null)
            throw new IllegalArgumentException("O identificador do método de pagamento não foi informado.");

        return PaymentMethod.<PaymentMethod>findByIdOptional(id)
                .orElseThrow(() -> new NotFoundException("Método de pagamento (" + id + ") não encontrado."));
    }
}
