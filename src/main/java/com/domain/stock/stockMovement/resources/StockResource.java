package com.domain.stock.stockMovement.resources;

import com.domain.commercial.product.Product;
import com.domain.commercial.productBatch.ProductBatch;
import com.domain.stock.stockMovement.dtos.ProductBatchStockOutputDto;
import com.domain.stock.stockMovement.mappers.StockMovementDtoMapper;
import com.domain.stock.stockMovement.services.StockMovementService;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;

@Path("/stock")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class StockResource {
    @Inject
    StockMovementService stockMovementService;

    @GET
    @Path("/product/{productId}")
    @RolesAllowed("GET-ALL_PRODUCT")
    public Response getProductStock(@PathParam("productId") Long productId) {
        Product product = Product.getByIdOrThrow(productId);

        List<ProductBatch> batches = ProductBatch.list(
                "productSupplier.product.id = ?1 order by expirationDate nulls last",
                product.id
        );

        List<ProductBatchStockOutputDto> stock = batches.stream()
                .map(batch -> StockMovementDtoMapper.toStockDto(
                        batch,
                        stockMovementService.getAvailableQuantityByBatch(batch)
                ))
                .toList();

        return Response.ok(stock).build();
    }
}