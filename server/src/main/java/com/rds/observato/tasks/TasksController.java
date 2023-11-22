package com.rds.observato.tasks;

import com.codahale.metrics.annotation.Timed;
import com.rds.observato.api.persistence.Repository;
import com.rds.observato.api.request.CreateTaskRequest;
import com.rds.observato.api.response.CreateTaskResponse;
import com.rds.observato.auth.Authoriser;
import com.rds.observato.auth.Role;
import com.rds.observato.auth.User;
import com.rds.observato.validation.Validator;
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
      @Auth User user, @PathParam("account") Long account, CreateTaskRequest request) {
    Validator.checkIsNullOrNegative(account, "account");
    Validator.checkIsNull(request, "request");
    Authoriser.check(user, Role.ADMIN);
    return new CreateTaskResponse(
        repository.tasks().create(account, request.name(), request.description()));
  }

  @GET
  public GetTasksResponse get(@Auth User user, @PathParam("account") long account) {
    Authoriser.check(user, Role.ADMIN);
    return new GetTasksResponse(
        repository.tasks().findAll(account).stream()
            .map(GetTaskResponse::from)
            .collect(Collectors.toSet()));
  }
}
