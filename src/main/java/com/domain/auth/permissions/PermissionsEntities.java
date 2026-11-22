package com.domain.auth.permissions;

import com.domain.shared.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "PERMISSIONS_ENTITIES", schema = "AUTH")
public class PermissionsEntities extends BaseEntity {
    @Column(name = "NAME", insertable = false, updatable = false)
    public String name;
}