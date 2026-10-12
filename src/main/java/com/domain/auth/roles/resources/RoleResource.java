package com.domain.auth.roles.resources;

import com.domain.auth.roles.Roles;
import com.domain.auth.roles.dtos.RoleInputDto;
import com.domain.auth.roles.dtos.RoleOutputDto;
import com.domain.auth.roles.mappers.RoleDtoMapper;
import jakarta.annotation.security.RolesAllowed;
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

@Path("/role")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class RoleResource {
    @POST
    @Transactional
    @RolesAllowed("CREATE-ROLE")
    public Response createRole(
            @NotNull(message = "Deve ser informado os dados da função.")
            @Valid
            RoleInputDto dto
    ) {
        Roles role = Roles.create(dto.name());

        RoleOutputDto roleOutputDto = RoleDtoMapper.toDto(role);

        URI uri = URI.create("/role/" + roleOutputDto.id());

        return Response.created(uri)
                .entity(roleOutputDto)
                .build();
    }
}
