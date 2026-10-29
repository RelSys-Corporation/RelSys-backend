package com.domain.auth.roles.resources;

import com.domain.auth.roles.Roles;
import com.domain.auth.roles.dtos.RoleInputDto;
import com.domain.auth.roles.dtos.RoleOutputDto;
import com.domain.auth.roles.mappers.RoleDtoMapper;
import com.domain.auth.rolesPermissions.RolesPermissions;
import com.domain.auth.rolesPermissions.dtos.PermissionsInputDto;
import com.domain.auth.rolesPermissions.dtos.RolesPermissionsOutputDto;
import com.domain.auth.rolesPermissions.mappers.RolesPermissionsDtoMapper;
import com.domain.auth.rolesPermissions.services.RolesPermissionsService;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.net.URI;
import java.util.List;

@Path("/role")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class RoleResource {
    @Inject
    RolesPermissionsService rolesPermissionsService;

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

    @POST
    @Transactional
    @Path("/{id}/permissions")
    @RolesAllowed("GIVE-PERMISSION")
    public Response givePermissionsToRole(
            @PathParam("id")
            @NotNull(message = "Deve ser informada a role.")
            Long roleId,
            @NotNull(message = "Deve ser informado as permissões.")
            @NotEmpty(message = "A permissão deve ser enviada.")
            List<@Valid PermissionsInputDto> permissionsDto
    ) {
        List<RolesPermissions> rolesPermissions = rolesPermissionsService.givePermissionsToRole(
                roleId,
                permissionsDto
        );

        List<RolesPermissionsOutputDto> rolesPermissionsOutputDto = RolesPermissionsDtoMapper.toDto(rolesPermissions);

        URI uri = URI.create("/role/" + roleId + "/permissions");

        return Response.created(uri)
                .entity(rolesPermissionsOutputDto)
                .build();
    }

    @GET
    @Path("/all")
    @RolesAllowed("GET-ALL_ROLES")
    public Response getAllRoles() {
        List<RoleOutputDto> roles = Roles.<Roles>findAll()
                .list()
                .stream()
                .map(RoleDtoMapper::toDto)
                .toList();

        return Response.ok(roles).build();
    }
}
