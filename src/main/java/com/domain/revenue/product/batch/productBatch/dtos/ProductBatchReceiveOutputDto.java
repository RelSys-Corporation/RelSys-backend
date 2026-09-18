package com.domain.revenue.product.batch.productBatch.dtos;

import io.quarkus.runtime.annotations.RegisterForReflection;

@RegisterForReflection
public record ProductBatchReceiveOutputDto(
        Long id
) {
}
