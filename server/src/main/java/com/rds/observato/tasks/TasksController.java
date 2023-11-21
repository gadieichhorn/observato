package com.rds.observato.tasks;

import com.codahale.metrics.annotation.Timed;
import com.rds.observato.api.persistence.Repository;
import com.rds.observato.api.request.CreateTaskRequest;
import com.rds.observato.api.response.CreateTaskResponse;
import com.rds.observato.auth.User;
import io.dropwizard.auth.Auth;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import java.util.stream.Collectors;

@Timed
@Path("tasks/{account}")
@Produces(MediaType.APPLICATION_JSON)
public record TasksController(Repository repository) {

  @POST
  public CreateTaskResponse create(
      @Auth User user, @PathParam("account") long account, CreateTaskRequest request) {
    return new CreateTaskResponse(
        repository.tasks().create(account, request.name(), request.description()));
  }

  @GET
  public GetTasksResponse get(@Auth User user, @PathParam("account") long account) {
    return new GetTasksResponse(
        repository.tasks().findAll(account).stream()
            .map(GetTaskResponse::from)
            .collect(Collectors.toSet()));
  }
}
