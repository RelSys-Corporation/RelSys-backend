package com.infrastructure.security.exceptionTreatment;

import com.infrastructure.security.exceptionTreatment.dtos.ErrorResponseDto;
import io.quarkus.security.ForbiddenException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class ForbiddenExceptionMapper implements ExceptionMapper<ForbiddenException> {
    @Override
    public Response toResponse(ForbiddenException exception) {
        ErrorResponseDto error = new ErrorResponseDto(
                "Usuário sem permissão.",
                Response.Status.FORBIDDEN.getStatusCode()
        );

        return Response.status(error.status())
                .entity(error)
                .build();
    }
}
