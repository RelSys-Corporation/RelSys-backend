package com.domain.revenue.sale.enums.saleStatus;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "SALE_STATUS")
public class SaleStatus extends PanacheEntityBase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    @Column(name = "NAME", length = 100, nullable = false, unique = true)
    public String name;

    @Column(name = "CREATED_AT", nullable = false)
    public LocalDateTime createdAt;
}
