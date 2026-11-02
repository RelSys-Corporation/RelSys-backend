package com.domain.stock.productBatch.dtos;

import io.quarkus.runtime.annotations.RegisterForReflection;

@RegisterForReflection
public record ProductBatchReceiveOutputDto(
        Long id
) {
}
