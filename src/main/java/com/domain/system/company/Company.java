package com.domain.system.company;

import com.domain.shared.BaseEntity;
import com.infrastructure.exceptions.NotFoundException;
import jakarta.persistence.*;

@Entity
@Table(name = "COMPANY", schema = "SYSTEM")
public class Company extends BaseEntity {
    @Column(name = "CORPORATE_NAME", length = 200, nullable = false, unique = true)
    String corporateName;

    @Column(name = "TRADE_NAME", length = 200)
    String tradeName;

    @Column(name = "IS_HEADQUARTER", nullable = false)
    boolean isHeadquarter;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "HEADQUARTER_COMPANY_ID")
    Company HeadquarterCompany;

    protected Company() {}

    public static Company getById(Long id) {
        if (id == null)
            return null;

        return Company.findById(id);
    }

    public static Company getByIdOrThrow(Long id) {
        if (id == null)
            return null;

        return Company.<Company>findByIdOptional(id)
                .orElseThrow(() -> new NotFoundException("Empresa (" + id + ") não encontrada."));
    }
}
