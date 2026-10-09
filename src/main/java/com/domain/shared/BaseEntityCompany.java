package com.domain.shared;

import com.domain.system.company.Company;
import jakarta.persistence.*;
import org.hibernate.annotations.TenantId;

@MappedSuperclass
public abstract class BaseEntityCompany extends BaseEntity {
    @TenantId
    @Column(name = "COMPANY_ID", nullable = false)
    protected Long companyId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "COMPANY_ID", insertable = false, updatable = false)
    protected Company company;
}
