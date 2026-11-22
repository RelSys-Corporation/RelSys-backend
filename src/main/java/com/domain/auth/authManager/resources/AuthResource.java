package com.domain.auth.authManager.resources;

import com.domain.auth.authManager.dtos.LoginInputDto;
import com.domain.auth.authManager.dtos.LoginOutputDto;
import com.domain.auth.authManager.dtos.PermissionsOutputDto;
import com.domain.auth.authManager.dtos.RegisterInputDto;
import com.domain.auth.authManager.mappers.LoginDtoMapper;
import com.domain.auth.authManager.services.AuthService;
import com.domain.auth.authManager.services.TokenService;
import com.domain.auth.permissions.Permissions;
import com.domain.auth.permissions.services.PermissionsService;
import com.domain.auth.roles.Roles;
import com.domain.auth.rolesPermissions.RolesPermissions;
import com.domain.auth.users.Users;
import com.domain.system.message.Message;
import com.infrastructure.annotations.RequiredField;
import com.infrastructure.exceptions.InvalidCredentialException;
import io.quarkus.security.Authenticated;
import jakarta.annotation.security.PermitAll;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.NewCookie;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.jwt.JsonWebToken;

import java.net.URI;
import java.util.List;
import java.util.Set;

@Path("/auth")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class AuthResource {
    @Inject
    AuthService authService;

    @Inject
    PermissionsService permissionsService;

    @Inject
    JsonWebToken jwt;

    @POST
    @Path("/register")
    @Transactional
    @RolesAllowed("CREATE-USER")
    public Response register(RegisterInputDto dto) {
        Users user = authService.register(dto.userName(), dto.userPassword(), dto.roleId());

        URI uri = URI.create("/user/" + user.id);

        return Response.created(uri).build();
    }

    @POST
    @Path("/login")
    @Transactional
    @PermitAll
    public Response login(LoginInputDto dto) {
        NewCookie authCookie = authService.processLogin(
                dto.userName(),
                dto.userPassword(),
                dto.companyId()
        );

        return Response.ok()
                .cookie(authCookie)
                .build();
    }

    @GET
    @Path("permission")
    @Authenticated
    public Response gerPermissions() {
        Set<String> group = jwt.getGroups();
        if (group == null || group.isEmpty())
            return Response.ok().build();

        Roles role = Roles.getByName(group.iterator().next());
        List<Permissions> permissions = RolesPermissions.getPermissionByRole(role);
        List<PermissionsOutputDto> dto = permissionsService.getMethodsByPermissions(permissions);

        return Response.ok(dto).build();
    }

    @POST
    @Path("/firstUser")
    @Transactional
    @PermitAll
    public Response createFirstUser(RegisterInputDto dto) {
        Users user = authService.createFirstUser(dto.userName(), dto.userPassword(), dto.roleId());

        URI uri = URI.create("/user/" + user.id);

        return Response.created(uri).build();
    }

    @PUT
    @Path("/user/{id}")
    @Transactional
    @RolesAllowed("UPDATE-USER-COMPANIES")
    public Response updateUserCompanies(
            @PathParam("id") Long userId,
            @RequiredField(entity = Message.Entity.COMPANY)
            List<Long> companiesId
    ) {
        authService.updateUsersCompanies(userId, companiesId);
        return Response.ok().build();
    }
}
