package com.rds.observato.assignments;

import com.codahale.metrics.annotation.Timed;
import com.rds.observato.api.persistence.Repository;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;

@Timed
@Path("assignments/{account}/{assignment}")
@Produces(MediaType.APPLICATION_JSON)
public record AssignmentController(Repository repository) {

  @GET
  public GetAssignmentResponse get(
      @PathParam("account") long account, @PathParam("assignment") long assignment) {
    return repository
        .assignments()
        .findById(account, assignment)
        .map(GetAssignmentResponse::from)
        .orElseThrow(() -> new RuntimeException("Not found"));
  }
}
