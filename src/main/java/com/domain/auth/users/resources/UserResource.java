package com.domain.auth.users.resources;

import com.domain.auth.users.Users;
import com.domain.auth.users.dtos.UserCompanyOutputDto;
import com.domain.auth.users.dtos.UserOutputDto;
import com.domain.auth.users.mappers.UserDtoMapper;
import com.domain.auth.users.services.UserService;
import io.quarkus.security.Authenticated;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.jwt.JsonWebToken;

import java.util.List;

@Path("/user")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class UserResource {
    @Inject
    JsonWebToken jwt;

    @Inject
    UserService userService;

    @GET
    @Path("/all")
    @RolesAllowed("GET-ALL_USER")
    public Response getAllUsers() {
        List<UserOutputDto> users = Users.<Users>findAll()
                .stream()
                .map(UserDtoMapper::toDto)
                .toList();

        return Response.ok(users).build();
    }

    @GET
    @Path("/companies")
    @Authenticated
    public Response getUserAllowedCompanies() {
        Long userId = Long.valueOf(jwt.getSubject());
        List<UserCompanyOutputDto> companies = userService.getUserAllowedCompanies(userId);

        return Response.ok(companies).build();
    }
}
