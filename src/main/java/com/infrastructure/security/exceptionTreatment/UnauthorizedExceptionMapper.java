package com.infrastructure.security.exceptionTreatment;

import com.infrastructure.security.exceptionTreatment.dtos.ErrorResponseDto;
import io.quarkus.security.ForbiddenException;
import io.quarkus.security.UnauthorizedException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class UnauthorizedExceptionMapper implements ExceptionMapper<UnauthorizedException> {
    @Override
    public Response toResponse(UnauthorizedException exception) {
        exception.printStackTrace();
        ErrorResponseDto error = new ErrorResponseDto(
                "Usuário sem permissão.",
                Response.Status.UNAUTHORIZED.getStatusCode()
        );

        return Response.status(error.status())
                .entity(error)
                .build();
    }
}
