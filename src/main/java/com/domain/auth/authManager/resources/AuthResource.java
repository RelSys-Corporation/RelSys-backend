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
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.net.URI;

@Path("/auth")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class AuthResource {
    @POST
    @Path("/register")
    @Transactional
    @RolesAllowed("CREATE-USER")
    public Response register(RegisterInputDto dto) {
        Users user = AuthService.register(dto.userName(), dto.userPassword(), dto.roleId());

        URI uri = URI.create("/user/" + user.id);

        return Response.created(uri).build();
    }

    @POST
    @Path("/login")
    @Transactional
    @PermitAll
    public Response login(LoginInputDto dto) {
        String token = AuthService.processLogin(
                dto.userName(),
                dto.userPassword(),
                dto.companyId()
        );

        LoginOutputDto loginOutputDto = LoginDtoMapper.toDto(token);

        return Response.ok(loginOutputDto).build();
    }
}
