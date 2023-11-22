package com.rds.observato.jdbi;

import io.dropwizard.jersey.errors.ErrorMessage;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import java.sql.SQLException;

public class SqlExceptionMapper implements ExceptionMapper<SQLException> {

  @Override
  public Response toResponse(final SQLException exception) {
    if (PostgresErrorCodes.UNIQUE_VALIDATION_ERROR.equals(exception.getSQLState())) {
      return Response.status(Response.Status.CONFLICT)
          .type(MediaType.APPLICATION_JSON_TYPE)
          .entity(new ErrorMessage(Response.Status.CONFLICT.getStatusCode(), "Already exists!"))
          .build();
    }
    final long id = LogExceptionFormatter.logException(exception);
    return Response.serverError()
        .type(MediaType.APPLICATION_JSON_TYPE)
        .entity(new ErrorMessage(LogExceptionFormatter.formatErrorMessage(id)))
        .build();
  }
}
