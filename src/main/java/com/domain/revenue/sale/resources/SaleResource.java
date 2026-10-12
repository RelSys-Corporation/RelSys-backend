package com.domain.revenue.sale.resources;

import com.domain.revenue.sale.Sale;
import com.domain.revenue.sale.dtos.SaleInputDto;
import com.domain.revenue.sale.dtos.SaleOutputDto;
import com.domain.revenue.sale.mappers.SaleDtoMapper;
import com.domain.revenue.sale.services.SaleService;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
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
    public Response createSale(SaleInputDto dto) {
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
