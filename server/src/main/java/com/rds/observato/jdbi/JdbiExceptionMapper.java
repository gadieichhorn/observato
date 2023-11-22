package com.rds.observato.jdbi;

import io.dropwizard.jersey.errors.ErrorMessage;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import java.sql.SQLException;
import org.jdbi.v3.core.JdbiException;

public class JdbiExceptionMapper implements ExceptionMapper<JdbiException> {

  @Override
  public Response toResponse(final JdbiException exception) {
    final Throwable cause = exception.getCause();
    if (cause instanceof SQLException sqlException) {
      if (PostgresErrorCodes.UNIQUE_VALIDATION_ERROR.equals(sqlException.getSQLState())) {
        return Response.status(Response.Status.CONFLICT)
            .type(MediaType.APPLICATION_JSON_TYPE)
            .entity(new ErrorMessage(Response.Status.CONFLICT.getStatusCode(), "Already exists!"))
            .build();
      } else {
        return response(sqlException);
      }
    }
    return response(exception);
  }

  private Response response(final Throwable exception) {
    final long id = LogExceptionFormatter.logException(exception);
    return Response.serverError()
        .type(MediaType.APPLICATION_JSON_TYPE)
        .entity(new ErrorMessage(LogExceptionFormatter.formatErrorMessage(id)))
        .build();
  }
}
