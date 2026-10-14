package com.domain.revenue.sale.services;

import com.domain.financial.paymentMethod.PaymentMethod;
import com.domain.revenue.person.subtype.Client;
import com.domain.revenue.product.Product;
import com.domain.revenue.product.batch.productBatch.ProductBatch;
import com.domain.revenue.product.batch.productBatch.services.ProductBatchService;
import com.domain.revenue.sale.Sale;
import com.domain.revenue.sale.constant.saleStatus.SaleStatus;
import com.domain.revenue.sale.dtos.ProductSaleInputDto;
import com.domain.revenue.sale.productSale.ProductSale;
import com.domain.revenue.sale.productSale.productSaleBatch.ProductSaleBatch;
import com.infrastructure.exceptions.DomainException;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class SaleService {
    @Inject
    ProductBatchService productBatchService;

    public Sale createSale(
            Long clientId,
            Long paymentMethodId,
            BigDecimal saleDiscountPercentage,
            List<ProductSaleInputDto> products
    ) {
        Client client = Client.getById(clientId);
        PaymentMethod paymentMethod = PaymentMethod.getByIdOrThrow(paymentMethodId);
        SaleStatus saleStatus = SaleStatus.of(SaleStatus.Type.PROCESSED);

        Sale sale = Sale.create(
                client,
                paymentMethod,
                saleDiscountPercentage,
                saleStatus
        );

        List<ProductSale> productSales = new ArrayList<>();

        for (ProductSaleInputDto productSaleInputDto : products) {
            Product product = Product.getByIdOrThrow(productSaleInputDto.productId());
            ProductSale productSale = ProductSale.create(
                    sale,
                    product,
                    productSaleInputDto.quantity(),
                    productSaleInputDto.discountPercentage()
            );

            createProductSaleBatch(productSale);
        }

        return sale;
    }

    private List<ProductSaleBatch> createProductSaleBatch(ProductSale productSale) {
        List<ProductSaleBatch> productSaleBatches = new ArrayList<>();

        BigDecimal quantityToDeduct = productSale.quantity;
        List<ProductBatch> availableBatches = productBatchService.getProductBatchWithStockByProduct(productSale.product);

        BigDecimal totalAvailable = availableBatches.stream()
                .map(batch -> batch.remainingQuantity)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        if (totalAvailable.compareTo(quantityToDeduct) < 0)
            throw new DomainException("Estoque insuficiente para o produto: " + productSale.product.name +
                    ". Necessário: " + quantityToDeduct + ", Disponivel: " + totalAvailable);

        for (ProductBatch productBatch : availableBatches) {
            if (quantityToDeduct.compareTo(BigDecimal.ZERO) <= 0)
                break;

            BigDecimal quantityFromThisBatch = quantityToDeduct.min(productBatch.remainingQuantity);

            productBatch.remainingQuantity = productBatch.remainingQuantity.subtract(quantityFromThisBatch);
            productBatch.persist();

            ProductSaleBatch productSaleBatch = ProductSaleBatch.create(
                    productSale,
                    productBatch,
                    quantityFromThisBatch
            );

            productSaleBatches.add(productSaleBatch);

            quantityToDeduct = quantityToDeduct.subtract(quantityFromThisBatch);
        }

        return productSaleBatches;
    }
}