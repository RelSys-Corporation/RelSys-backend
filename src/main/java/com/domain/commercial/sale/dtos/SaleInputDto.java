package com.domain.commercial.sale.dtos;

import com.domain.system.message.Message;
import com.infrastructure.annotations.RequiredField;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.util.List;

public record SaleInputDto(
        Long clientId,
        @RequiredField(entity = Message.Entity.PAYMENT_METHOD)
        Long paymentMethodId,
        BigDecimal discountPercentage,
        @RequiredField(entity = Message.Entity.PRODUCT)
        List<@Valid ProductSaleInputDto> products
) {
}
