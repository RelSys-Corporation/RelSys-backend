package com.infrastructure.security.exceptionTreatment;

import com.infrastructure.exceptions.InvalidCredentialException;
import com.infrastructure.security.exceptionTreatment.dtos.ErrorResponseDto;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class InvalidCredentialExceptionMapper implements ExceptionMapper<InvalidCredentialException> {
    @Override
    public Response toResponse(InvalidCredentialException exception) {
        ErrorResponseDto error = new ErrorResponseDto(
                exception.getMessage(),
                Response.Status.UNAUTHORIZED.getStatusCode()
        );

        return Response.status(error.status())
                .entity(error)
                .build();
    }
}
