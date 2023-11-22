package com.rds.observato.assignments;

import com.codahale.metrics.annotation.Timed;
import com.rds.observato.api.persistence.Repository;
import com.rds.observato.auth.Authoriser;
import com.rds.observato.auth.Role;
import com.rds.observato.auth.User;
import com.rds.observato.validation.Validator;
import io.dropwizard.auth.Auth;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import java.util.stream.Collectors;

@Timed
@Path("assignments/{account}")
@Produces(MediaType.APPLICATION_JSON)
public record AssignmentsController(Repository repository) {

  @POST
  public CreateAssignmentResponse create(
      @Auth User user, @PathParam("account") Long account, CreateAssignmentRequest request) {
    Authoriser.check(user, Role.ADMIN);
    Validator.checkIsNullOrNegative(account, "account");
    Validator.checkIsNull(request, "request");
    return new CreateAssignmentResponse(
        repository
            .assignments()
            .create(account, request.task(), request.resource(), request.start(), request.end()));
  }

  @GET
  public GetAssignmentsResponse get(@Auth User user, @PathParam("account") long account) {
    Authoriser.check(user, Role.ADMIN);
    Validator.checkIsNullOrNegative(account, "account");
    return new GetAssignmentsResponse(
        repository.assignments().getAll(account).stream()
            .map(GetAssignmentResponse::from)
            .collect(Collectors.toSet()));
  }
}
