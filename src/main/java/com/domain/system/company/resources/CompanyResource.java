package com.domain.system.company.resources;

import com.domain.system.company.Company;
import com.domain.system.company.dtos.CompanyInputDto;
import com.domain.system.company.dtos.CompanyOutputDto;
import com.domain.system.company.mappers.CompanyDtoMapper;
import com.domain.system.company.services.CompanyService;
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

@Path("/company")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class CompanyResource {
    @Inject
    CompanyService companyService;

    @POST
    @Transactional
    @RolesAllowed("CREATE-COMPANY")
    public Response createCompany(
            @NotNull(message = "Devem ser informado os dados para criação da empresa.")
            @Valid
            CompanyInputDto dto
    ) {
        Company company = companyService.createCompany(
                dto.corporateName(),
                dto.tradeName(),
                dto.isHeadquarter(),
                dto.companyId()
        );

        CompanyOutputDto companyOutputDto = CompanyDtoMapper.toDto(company);

        URI uri = URI.create("/company/" + companyOutputDto.id());

        return Response.created(uri)
                .entity(companyOutputDto)
                .build();
    }
}
