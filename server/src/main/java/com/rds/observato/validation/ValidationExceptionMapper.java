package com.rds.observato.validation;

import io.dropwizard.jersey.errors.ErrorMessage;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;

public class ValidationExceptionMapper implements ExceptionMapper<ValidationException> {

  @Override
  public Response toResponse(final ValidationException exception) {
    return Response.status(Response.Status.BAD_REQUEST)
        .type(MediaType.APPLICATION_JSON_TYPE)
        .entity(
            new ErrorMessage(Response.Status.BAD_REQUEST.getStatusCode(), exception.getMessage()))
        .build();
  }
}
