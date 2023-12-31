package com.rds.observato.projects;

import com.codahale.metrics.annotation.Timed;
import com.rds.observato.auth.Authoriser;
import com.rds.observato.auth.Role;
import com.rds.observato.auth.User;
import com.rds.observato.db.Repository;
import com.rds.observato.validation.Validator;
import io.dropwizard.auth.Auth;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Timed
@Path("projects/{project}")
@Produces(MediaType.APPLICATION_JSON)
public record ProjectController(Repository repository) {

  @GET
  public GetProjectResponse get(@Auth User user, @PathParam("project") Long project) {
    Authoriser.check(user, Role.ADMIN);
    Validator.checkIsNullOrNegative(project, "project");
    return repository
        .projects()
        .findById(user.account(), project)
        .map(GetProjectResponse::from)
        .orElseThrow(() -> new RuntimeException("Not found"));
  }
}
