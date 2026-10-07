package com.domain.revenue.supplier.resources;

import com.domain.revenue.supplier.Supplier;
import com.domain.revenue.supplier.dtos.SupplierInputDto;
import com.domain.revenue.supplier.dtos.SupplierOutputDto;
import com.domain.revenue.supplier.mappers.SupplierDtoMapper;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Application;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.net.URI;

@Path("/supplier")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class SupplierResource {
    @GET
    public Response getAllSuppliers() {
        return Response.ok(Supplier.<Supplier>listAll().stream()
                        .map(SupplierDtoMapper::toDto)
                        .toList()
        ).build();
    }
}
