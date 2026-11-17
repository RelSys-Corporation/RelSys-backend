package com.domain.commercial.sale.resources;

import com.domain.commercial.sale.Sale;
import com.domain.commercial.sale.dtos.SaleInputDto;
import com.domain.commercial.sale.dtos.SaleOutputDto;
import com.domain.commercial.sale.mappers.SaleDtoMapper;
import com.domain.commercial.sale.services.SaleService;
import com.domain.system.message.Message;
import com.infrastructure.annotations.RequiredField;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.net.URI;

@Path("/sale")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class SaleResource {
    @Inject
    SaleService saleService;

    @POST
    @Transactional
    @RolesAllowed("CREATE-SALE")
    public Response createSale(
            @RequiredField(entity = Message.Entity.SALE, property = Message.Property.DATA)
            @Valid
            SaleInputDto dto
    ) {
        Sale sale = saleService.createSale(
                dto.clientId(),
                dto.paymentMethodId(),
                dto.discountPercentage(),
                dto.products()
        );

        SaleOutputDto saleOutputDto = SaleDtoMapper.toDto(sale);

        URI uri = URI.create("/sale/" + saleOutputDto.id());

        return Response
                .created(uri)
                .entity(saleOutputDto)
                .build();
    }
}
