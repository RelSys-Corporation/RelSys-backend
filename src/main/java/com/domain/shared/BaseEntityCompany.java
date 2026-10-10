package com.domain.shared;

import com.domain.system.company.Company;
import jakarta.persistence.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.TenantId;
import org.hibernate.type.SqlTypes;

@MappedSuperclass
public abstract class BaseEntityCompany extends BaseEntity {
    @TenantId
    @JdbcTypeCode(SqlTypes.BIGINT)
    @Column(name = "COMPANY_ID", nullable = false)
    protected String companyId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "COMPANY_ID", insertable = false, updatable = false)
    protected Company company;
}
