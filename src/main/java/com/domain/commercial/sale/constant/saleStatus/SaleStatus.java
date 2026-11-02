package com.domain.revenue.sale.constant.saleStatus;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "SALE_STATUS", schema = "REVENUE")
public class SaleStatus extends PanacheEntityBase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    @Column(name = "NAME", length = 100, nullable = false, unique = true)
    public String name;

    @Column(name = "CREATED_AT", nullable = false)
    public LocalDateTime createdAt;

    public enum Type {
        PROCESSED(1L, "Processado"),
        CANCELED(2L, "Cancelado");

        final Long id;
        final String description;

        Type(Long id, String description) {
            this.id = id;
            this.description = description;
        }

        public Long getId() {
            return this.id;
        }

        public String getDescription() {
            return this.description;
        }
    }

    protected SaleStatus() {}

    public static SaleStatus of(Type status) {
        return SaleStatus.<SaleStatus>findByIdOptional(status.id)
                .orElseThrow(() -> new IllegalStateException(
                        String.format(
                                "Status '%s' (%d) não encontrado na tabela SALE_STATUS.",
                                status.getDescription(),
                                status.getId()
                        )
                ));
    }
}
