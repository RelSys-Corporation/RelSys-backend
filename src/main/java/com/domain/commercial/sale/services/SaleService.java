package com.domain.commercial.sale.services;

import com.domain.financial.paymentMethod.PaymentMethod;
import com.domain.crm.person.subtype.Client;
import com.domain.commercial.product.Product;
import com.domain.commercial.productBatch.ProductBatch;
import com.domain.commercial.productBatch.services.ProductBatchService;
import com.domain.stock.stockMovement.StockMovement;
import com.domain.stock.stockMovement.services.StockMovementService;
import com.domain.stock.stockMovementOrigin.StockMovementOrigin;
import com.domain.commercial.sale.Sale;
import com.domain.commercial.sale.constant.saleStatus.SaleStatus;
import com.domain.commercial.sale.dtos.ProductSaleInputDto;
import com.domain.commercial.sale.productSale.ProductSale;
import com.domain.stock.productSaleBatch.ProductSaleBatch;
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

    @Inject
    StockMovementService stockMovementService;

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

        StockMovementOrigin saleOrigin = StockMovementOrigin.createForSale(sale);

        List<ProductSale> productSales = new ArrayList<>();

        for (ProductSaleInputDto productSaleInputDto : products) {
            Product product = Product.getByIdOrThrow(productSaleInputDto.productId());
            ProductSale productSale = ProductSale.create(
                    sale,
                    product,
                    productSaleInputDto.quantity(),
                    productSaleInputDto.discountPercentage()
            );

            createProductSaleBatch(productSale, saleOrigin);
        }

        return sale;
    }

    private List<ProductSaleBatch> createProductSaleBatch(ProductSale productSale, StockMovementOrigin saleOrigin) {
        List<ProductSaleBatch> productSaleBatches = new ArrayList<>();

        BigDecimal quantityToDeduct = productSale.quantity;
        List<ProductBatch> availableBatches = productBatchService.getProductBatchWithStockByProduct(productSale.product);

        BigDecimal totalAvailable = availableBatches.stream()
                .map(batch -> stockMovementService.getAvailableQuantityByBatch(batch))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        if (totalAvailable.compareTo(quantityToDeduct) < 0)
            throw new DomainException("Estoque insuficiente para o produto: " + productSale.product.name +
                    ". Necessário: " + quantityToDeduct + ", Disponivel: " + totalAvailable);

        for (ProductBatch productBatch : availableBatches) {
            if (quantityToDeduct.compareTo(BigDecimal.ZERO) <= 0)
                break;

            BigDecimal batchAvailableQuantity = stockMovementService.getAvailableQuantityByBatch(productBatch);
            BigDecimal quantityFromThisBatch = quantityToDeduct.min(batchAvailableQuantity);

            StockMovement lastInputMovement = stockMovementService.getLastInputMovementByBatch(productBatch);

            stockMovementService.createSaleOutMovement(
                    productSale.product,
                    productBatch,
                    saleOrigin,
                    lastInputMovement.warehouse,
                    lastInputMovement.warehouseLocation,
                    quantityFromThisBatch
            );

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