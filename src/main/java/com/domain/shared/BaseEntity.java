package com.domain.shared;


import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@MappedSuperclass
public abstract class BaseEntity extends PanacheEntityBase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    @CreationTimestamp
    @Column(name = "CREATED_AT", updatable = false)
    public LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "UPDATED_AT")
    public LocalDateTime updatedAt;
}
