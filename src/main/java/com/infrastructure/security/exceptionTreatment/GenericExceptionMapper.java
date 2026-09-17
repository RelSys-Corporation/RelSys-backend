package com.infrastructure.security.exceptionTreatment;

import com.infrastructure.security.exceptionTreatment.dtos.ErrorResponseDto;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class GenericExceptionMapper implements ExceptionMapper<Throwable> {
    @Override
    public Response toResponse(Throwable exception) {
        exception.printStackTrace();

        ErrorResponseDto error =  new ErrorResponseDto(
                "Ocorreu um erro interno ao processar a requisição. Tente novamente mais tarde.",
                Response.Status.INTERNAL_SERVER_ERROR.getStatusCode()
        );

        return Response.status(error.status())
                .entity(error)
                .type(MediaType.APPLICATION_JSON)
                .build();
    }
}
