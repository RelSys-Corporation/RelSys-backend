package com.domain.revenue.sale.dtos;

import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record ProductSaleInputDto(
        @NotNull(message = "Deve ser informado o ID do produto.")
        Long productId,
        @NotNull(message = "Deve ser informada a quantidade de venda do produto.")
        BigDecimal quantity,
        BigDecimal discountPercentage
) {
}
