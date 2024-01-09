package com.rds.observato.tasks;

import com.codahale.metrics.annotation.Timed;
import com.rds.observato.auth.Authoriser;
import com.rds.observato.auth.Role;
import com.rds.observato.auth.User;
import com.rds.observato.db.Repository;
import com.rds.observato.validation.Validator;
import com.rds.observato.view.TaskView;
import io.dropwizard.auth.Auth;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Timed
@Path("tasks/{task}")
@Produces({MediaType.APPLICATION_JSON, MediaType.TEXT_HTML})
public record TaskController(Repository repository) {

  @GET
  public TaskView get(@Auth User user, @PathParam("task") long task) {
    Authoriser.check(user, Role.ADMIN);
    return repository
        .tasks()
        .finById(user.account(), task)
        .map(TaskView::new)
        .orElseThrow(
            () -> new WebApplicationException("Task not found", Response.Status.NOT_FOUND));
  }

  @PUT
  public UpdateTaskResponse put(
      @Auth User user, @PathParam("task") long task, UpdateTaskRequest request) {
    Authoriser.check(user, Role.ADMIN);
    Validator.checkIsNull(request, "request");
    return new UpdateTaskResponse(
        repository
            .tasks()
            .update(
                user.account(),
                request.id(),
                request.revision(),
                request.name(),
                request.description()));
  }
}
