package com.infrastructure.security.exceptionTreatment;

import com.infrastructure.exceptions.DomainException;
import com.infrastructure.security.exceptionTreatment.dtos.ErrorResponseDto;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class DomainExceptionMapper implements ExceptionMapper<DomainException> {
    @Override
    public Response toResponse(DomainException exception) {
        ErrorResponseDto error = new ErrorResponseDto(
                exception.getMessage(),
                Response.Status.BAD_REQUEST.getStatusCode()
        );

        return Response.status(error.status())
                .entity(error)
                .build();
    }
}
