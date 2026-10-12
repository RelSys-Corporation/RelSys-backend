package com.domain.revenue.sale.dtos;

import java.math.BigDecimal;
import java.util.List;

public record SaleInputDto(
        Long clientId,
        Long paymentMethodId,
        BigDecimal discountPercentage,
        List<ProductSaleInputDto> products
) {
}
