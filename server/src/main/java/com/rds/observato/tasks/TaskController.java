package com.rds.observato.tasks;

import com.codahale.metrics.annotation.Timed;
import com.rds.observato.api.persistence.Repository;
import com.rds.observato.auth.Authoriser;
import com.rds.observato.auth.Role;
import com.rds.observato.auth.User;
import com.rds.observato.validation.Validator;
import io.dropwizard.auth.Auth;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Timed
@Path("tasks/{account}/{task}")
@Produces(MediaType.APPLICATION_JSON)
public record TaskController(Repository repository) {

  @GET
  public GetTaskResponse get(
      @Auth User user, @PathParam("account") long account, @PathParam("task") long task) {
    Authoriser.check(user, Role.ADMIN);
    return repository
        .tasks()
        .finById(account, task)
        .map(GetTaskResponse::from)
        .orElseThrow(
            () ->
                new WebApplicationException("Task %d".formatted(task), Response.Status.NOT_FOUND));
  }

  @PUT
  public UpdateTaskResponse put(
      @Auth User user,
      @PathParam("account") long account,
      @PathParam("task") long task,
      UpdateTaskRequest request) {
    Authoriser.check(user, Role.ADMIN);
    Validator.checkIsNull(request, "request");
    return new UpdateTaskResponse(
        repository
            .tasks()
            .updateTask(
                account, request.id(), request.revision(), request.name(), request.description()));
  }
}
