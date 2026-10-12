package com.infrastructure.security.exceptionTreatment;

import com.infrastructure.security.exceptionTreatment.dtos.ErrorResponseDto;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

import java.util.stream.Collectors;

@Provider
public class ConstraintViolationExceptionMapper implements ExceptionMapper<ConstraintViolationException> {
    @Override
    public Response toResponse(ConstraintViolationException exception) {
        String errorMessage = exception.getConstraintViolations().stream()
                .map(ConstraintViolation::getMessage)
                .collect(Collectors.joining("; "));

        ErrorResponseDto error = new ErrorResponseDto(
                errorMessage,
                Response.Status.BAD_REQUEST.getStatusCode()
        );

        return Response.status(error.status())
                .entity(error)
                .build();
    }
}
