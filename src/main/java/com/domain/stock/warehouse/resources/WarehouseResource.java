package com.domain.stock.warehouse.resources;

import com.domain.stock.warehouse.Warehouse;
import com.domain.stock.warehouse.dtos.WarehouseInputDto;
import com.domain.stock.warehouse.dtos.WarehouseOutputDto;
import com.domain.stock.warehouse.mappers.WarehouseDtoMapper;
import com.domain.stock.warehouseLocation.WarehouseLocation;
import com.domain.stock.warehouseLocation.dtos.WarehouseLocationInputDto;
import com.domain.stock.warehouseLocation.dtos.WarehouseLocationOutputDto;
import com.domain.stock.warehouseLocation.mappers.WarehouseLocationDtoMapper;
import jakarta.annotation.security.RolesAllowed;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.net.URI;
import java.util.List;

@Path("/warehouse")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class WarehouseResource {
    @POST
    @Transactional
    @RolesAllowed("CREATE-WAREHOUSE")
    public Response createWarehouse(
            @NotNull(message = "Deve ser informado os dados do armazém.")
            @Valid
            WarehouseInputDto dto
    ) {
        Warehouse warehouse = Warehouse.create(dto.name());

        WarehouseOutputDto warehouseDto = WarehouseDtoMapper.toDto(warehouse);

        URI uri = URI.create("/warehouse/" + warehouseDto.id());

        return Response.created(uri)
                .entity(warehouseDto)
                .build();
    }

    @GET
    @Path("/all")
    @RolesAllowed("GET-ALL_WAREHOUSE")
    public Response getAllWarehouses() {
        List<WarehouseOutputDto> warehouses = Warehouse.<Warehouse>listAll().stream()
                .map(WarehouseDtoMapper::toDto)
                .toList();

        return Response.ok(warehouses).build();
    }

    @POST
    @Path("/{warehouseId}/location")
    @Transactional
    @RolesAllowed("CREATE-WAREHOUSE_LOCATION")
    public Response createWarehouseLocation(
            @PathParam("warehouseId")
            @NotNull(message = "Deve ser informado o armazém da localização.")
            Long warehouseId,
            @NotNull(message = "Deve ser informado os dados da localização.")
            @Valid
            WarehouseLocationInputDto dto
    ) {
        Warehouse warehouse = Warehouse.getByIdOrThrow(warehouseId);

        WarehouseLocation warehouseLocation = WarehouseLocation.create(
                warehouse,
                dto.street(),
                dto.rack(),
                dto.level(),
                dto.position(),
                dto.active() != null ? dto.active() : true
        );

        WarehouseLocationOutputDto warehouseLocationDto = WarehouseLocationDtoMapper.toDto(warehouseLocation);

        URI uri = URI.create("/warehouse/" + warehouseId + "/location/" + warehouseLocationDto.id());

        return Response.created(uri)
                .entity(warehouseLocationDto)
                .build();
    }

    @GET
    @Path("/{warehouseId}/location/all")
    @RolesAllowed("GET-ALL_WAREHOUSE_LOCATION")
    public Response getAllWarehouseLocations(
            @PathParam("warehouseId")
            @NotNull(message = "Deve ser informado o armazém das localizações.")
            Long warehouseId
    ) {
        Warehouse.getByIdOrThrow(warehouseId);

        List<WarehouseLocationOutputDto> locations = WarehouseLocation.<WarehouseLocation>list(
                        "warehouse.id = ?1",
                        warehouseId
                ).stream()
                .map(WarehouseLocationDtoMapper::toDto)
                .toList();

        return Response.ok(locations).build();
    }
}