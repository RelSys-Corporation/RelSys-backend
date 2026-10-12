package com.domain.revenue.sale.dtos;

import java.math.BigDecimal;

public record ProductSaleInputDto(
        Long productId,
        BigDecimal quantity,
        BigDecimal discountPercentage
) {
}
