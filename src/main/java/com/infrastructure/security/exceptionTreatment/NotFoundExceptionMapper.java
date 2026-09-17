package com.infrastructure.security.exceptionTreatment;

import com.infrastructure.exceptions.NotFoundException;
import com.infrastructure.security.exceptionTreatment.dtos.ErrorResponseDto;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;

public class NotFoundExceptionMapper implements ExceptionMapper<NotFoundException> {
    @Override
    public Response toResponse(NotFoundException exception) {
        ErrorResponseDto error = new ErrorResponseDto(
                exception.getMessage(),
                Response.Status.NOT_FOUND.getStatusCode()
        );

        return Response.status(error.status())
                .entity(error)
                .build();
    }
}
