package com.domain.revenue.supplier.dtos;

import io.quarkus.runtime.annotations.RegisterForReflection;

@RegisterForReflection
public record SupplierOutputDto(Long id, String name) {
}
