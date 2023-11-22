package com.rds.observato.assignments;

import com.codahale.metrics.annotation.Timed;
import com.rds.observato.api.persistence.Repository;
import com.rds.observato.auth.Authoriser;
import com.rds.observato.auth.Role;
import com.rds.observato.auth.User;
import io.dropwizard.auth.Auth;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;

@Timed
@Path("assignments/{account}/{assignment}")
@Produces(MediaType.APPLICATION_JSON)
public record AssignmentController(Repository repository) {

  @GET
  public GetAssignmentResponse get(
      @Auth User user,
      @PathParam("account") long account,
      @PathParam("assignment") long assignment) {
    Authoriser.check(user, Role.ADMIN);
    return repository
        .assignments()
        .findById(account, assignment)
        .map(GetAssignmentResponse::from)
        .orElseThrow(() -> new RuntimeException("Not found"));
  }
}
