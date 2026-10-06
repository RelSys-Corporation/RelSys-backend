package com.domain.shared;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;

public interface FindableById<Entity extends PanacheEntityBase, ID> {
    Entity getByIdOrThrow(ID id);
}
