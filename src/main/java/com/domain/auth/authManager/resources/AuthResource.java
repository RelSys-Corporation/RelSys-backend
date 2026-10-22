package com.domain.auth.authManager.resources;

import com.domain.auth.authManager.dtos.LoginInputDto;
import com.domain.auth.authManager.dtos.LoginOutputDto;
import com.domain.auth.authManager.dtos.RegisterInputDto;
import com.domain.auth.authManager.mappers.LoginDtoMapper;
import com.domain.auth.authManager.services.AuthService;
import com.domain.auth.authManager.services.TokenService;
import com.domain.auth.users.Users;
import jakarta.annotation.security.PermitAll;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.NewCookie;
import jakarta.ws.rs.core.Response;

import java.net.URI;

@Path("/auth")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class AuthResource {
    @Inject
    AuthService authService;

    @POST
    @Path("/register")
    @Transactional
    //@RolesAllowed("CREATE-USER")
    @PermitAll
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
}
