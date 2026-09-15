package com.infrastructure.security.exceptionTreatment;

import com.infrastructure.security.exceptionTreatment.dtos.ErrorResponseDto;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class InvalidArgumnetExceptionMapper implements ExceptionMapper<IllegalArgumentException> {
    @Override
    public Response toResponse(IllegalArgumentException exception) {
        ErrorResponseDto error = new ErrorResponseDto(
                exception.getMessage(),
                Response.Status.BAD_REQUEST.getStatusCode()
        );

        return Response.status(error.status())
                .entity(error)
                .build();
    }
}
