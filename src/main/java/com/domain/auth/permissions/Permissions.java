package com.domain.auth.permissions;

import com.domain.shared.BaseEntity;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "PERMISSIONS")
public class Permissions extends BaseEntity {
    @Column(name = "NAME", length = 100, nullable = false, unique = true)
    public String name;
}
