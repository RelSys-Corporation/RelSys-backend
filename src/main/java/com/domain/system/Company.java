package com.domain.system;

import com.domain.shared.BaseEntity;
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
}
