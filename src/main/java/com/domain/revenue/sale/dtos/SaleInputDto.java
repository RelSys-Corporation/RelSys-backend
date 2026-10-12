package com.domain.revenue.sale.dtos;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.util.List;

public record SaleInputDto(
        Long clientId,
        @NotNull(message = "O método de pagamento deve ser informado.")
        Long paymentMethodId,
        BigDecimal discountPercentage,
        @NotNull(message = "Deve ser informado os produtos da venda.")
        @NotEmpty(message = "Deve ser informado os produtos da venda.")
        @Valid
        List<ProductSaleInputDto> products
) {
}
