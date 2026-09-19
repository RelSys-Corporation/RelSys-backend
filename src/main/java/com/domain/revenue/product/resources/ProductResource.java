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
        List<ProductOutputDto> productOutputDtos = ProductSupplierService.getAllProductAndSuppliers()
                .entrySet()
                .stream()
                .map(entry -> ProductDtoMapper.toDto(entry.getKey(), entry.getValue()))
                .toList();

        return Response.ok().entity(productOutputDtos).build();
    }

    @GET
    @Path("/barcode")
    public Response getProductByBarcode(@QueryParam("barcode") String barcode) {
        return Product.getProductByBarcode(barcode)
                .map(product -> {
                    List<Supplier> suppliers = ProductSupplier.getSuppliersOfProduct(product);
                    return Response.ok().entity(
                            ProductDtoMapper.toDto(
                                    product,
                                    suppliers
                            )
                    ).build();
                })
                .orElseGet(() -> Response.status(Response.Status.NOT_FOUND).build());
    }

    @POST
    @Transactional
    public Response createProduct(ProductInputDto dto) {

        Product product = Product.create(
                dto.name(),
                dto.supplierId(),
                dto.price(),
                dto.barcode()
        );

        ProductOutputDto productDto = ProductDtoMapper.toDto(
                product,
                ProductSupplier.getSuppliersOfProduct(product)
        );

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

        URI uri = URI.create("productBatch/" + productBatchDto.id());

        return Response
                .created(uri)
                .entity(productBatchDto)
                .build();
    }
}
