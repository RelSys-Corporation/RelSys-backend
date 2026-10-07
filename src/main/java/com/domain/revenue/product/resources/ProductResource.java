package com.domain.revenue.product.resources;

import com.domain.revenue.product.Product;
import com.domain.revenue.product.batch.productBatch.ProductBatch;
import com.domain.revenue.product.batch.productBatch.dtos.ProductBatchReceiveInputDto;
import com.domain.revenue.product.batch.productBatch.dtos.ProductBatchReceiveOutputDto;
import com.domain.revenue.product.batch.productBatch.mappers.ProductBatchDtoMapper;
import com.domain.revenue.product.batch.productBatch.services.ProductBatchService;
import com.domain.revenue.product.dtos.ProductInputDto;
import com.domain.revenue.product.dtos.ProductOutputDto;
import com.domain.revenue.product.mappers.ProductDtoMapper;
import com.domain.revenue.product.suppliers.productSupplier.ProductSupplier;
import com.domain.revenue.product.suppliers.productSupplier.services.ProductSupplierService;
import com.domain.revenue.supplier.Supplier;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.net.URI;
import java.util.List;

@Path("/product")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ProductResource {
    @GET
    public Response getAllProducts() {
        List<ProductOutputDto> productOutputDtos = Product.<Product>listAll().stream()
                .map(ProductDtoMapper::toDto)
                .toList();

        return Response.ok(productOutputDtos).build();
    }

    @GET
    @Path("/barcode")
    public Response getProductByBarcode(@QueryParam("barcode") String barcode) {
        return Response.ok(ProductDtoMapper.toDto(
                Product.getProductByBarcode(barcode)
        )).build();
    }

    @POST
    @Transactional
    public Response createProduct(ProductInputDto dto) {
        Product product = ProductSupplierService.createProduct(
                dto.name(),
                dto.price(),
                dto.barcode(),
                dto.supplierId()
        );

        ProductOutputDto productDto = ProductDtoMapper.toDto(product);

        URI uri = URI.create("/product/" + productDto.id());

        return Response
                .created(uri)
                .entity(productDto)
                .build();
    }

    @POST
    @Path("/receive")
    @Transactional
    public Response createProductBatch(ProductBatchReceiveInputDto dto) {
        ProductBatch productBatch = ProductBatchService.createProductBatch(
                dto.productId(),
                dto.supplierId(),
                dto.purchasePrice(),
                dto.quantity()
        );

        ProductBatchReceiveOutputDto productBatchDto = ProductBatchDtoMapper.toDto(productBatch);

        URI uri = URI.create("/productBatch/" + productBatchDto.id());

        return Response
                .created(uri)
                .entity(productBatchDto)
                .build();
    }
}
