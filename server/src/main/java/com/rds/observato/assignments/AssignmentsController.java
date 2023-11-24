package com.rds.observato.assignments;

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
@Path("assignments")
@Produces(MediaType.APPLICATION_JSON)
public record AssignmentsController(Repository repository) {

  @POST
  public CreateAssignmentResponse post(@Auth User user, CreateAssignmentRequest request) {
    Authoriser.check(user, Role.ADMIN);
    Validator.checkIsNull(request, "request");
    return new CreateAssignmentResponse(
        repository
            .assignments()
            .create(
                user.account(),
                request.task(),
                request.resource(),
                request.start(),
                request.end()));
  }

  @GET
  public GetAssignmentsResponse get(@Auth User user) {
    Authoriser.check(user, Role.ADMIN);
    Validator.checkIsNullOrNegative(user.account(), "account");
    return new GetAssignmentsResponse(
        repository.assignments().getAll(user.account()).stream()
            .map(GetAssignmentResponse::from)
            .collect(Collectors.toSet()));
  }
}
