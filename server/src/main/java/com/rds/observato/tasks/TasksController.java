package com.rds.observato.tasks;

import com.codahale.metrics.annotation.Timed;
import com.rds.observato.Repository;
import com.rds.observato.auth.Authoriser;
import com.rds.observato.auth.Role;
import com.rds.observato.auth.User;
import com.rds.observato.validation.Validator;
import io.dropwizard.auth.Auth;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import java.util.stream.Collectors;

@Timed
@Path("tasks")
@Produces(MediaType.APPLICATION_JSON)
public record TasksController(Repository repository) {

  @POST
  public CreateTaskResponse create(@Auth User user, CreateTaskRequest request) {
    Validator.checkIsNullOrNegative(user.account(), "account");
    Validator.checkIsNull(request, "request");
    Authoriser.check(user, Role.ADMIN);
    return new CreateTaskResponse(
        repository.tasks().create(user.account(), request.name(), request.description()));
  }

  @GET
  public GetTasksResponse get(@Auth User user) {
    Authoriser.check(user, Role.ADMIN);
    return new GetTasksResponse(
        repository.tasks().findAll(user.account()).stream()
            .map(GetTaskResponse::from)
            .collect(Collectors.toSet()));
  }
}
