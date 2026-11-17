package com.domain.financial.financialTitle.constants;

import com.domain.shared.BaseEntity;
import com.domain.system.message.Message;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "FINANCIAL_TITLE_STATUS", schema = "FINANCIAL")
public class FinancialTitleStatus extends BaseEntity {
    @Column(name = "NAME", nullable = false)
    public String name;

    protected FinancialTitleStatus() {}

    public enum Type {
        PENDING,
        PARTIAL,
        PAID,
        CANCELED
    }

    public static FinancialTitleStatus of(Type status) {
        return FinancialTitleStatus.<FinancialTitleStatus>find("name = ?1", status.toString())
                .firstResultOptional()
                .orElseThrow(() -> new IllegalStateException(Message.format(Message.Type.NOT_FOUND, Message.Entity.FINANCIAL_TITLE, Message.Property.STATUS, status.name())));
    }
}