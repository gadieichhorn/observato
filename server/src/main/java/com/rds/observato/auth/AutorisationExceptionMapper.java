package com.rds.observato.auth;

import io.dropwizard.jersey.errors.ErrorMessage;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;

public class AutorisationExceptionMapper implements ExceptionMapper<AuthorisedException> {

  @Override
  public Response toResponse(final AuthorisedException exception) {
    return Response.status(Response.Status.UNAUTHORIZED)
        .type(MediaType.APPLICATION_JSON_TYPE)
        .entity(new ErrorMessage(Response.Status.UNAUTHORIZED.getStatusCode(), "access denied"))
        .build();
  }
}
