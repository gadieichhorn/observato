package com.rds.observato.projects;

import com.codahale.metrics.annotation.Timed;
import com.google.common.collect.ImmutableSet;
import com.rds.observato.auth.Authoriser;
import com.rds.observato.auth.Role;
import com.rds.observato.auth.User;
import com.rds.observato.db.Repository;
import com.rds.observato.validation.Validator;
import io.dropwizard.auth.Auth;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;

@Timed
@Path("projects")
@Produces(MediaType.APPLICATION_JSON)
public record ProjectsController(Repository repository) {

  @POST
  public CreateProjectResponse post(@Auth User user, CreateProjectRequest request) {
    Authoriser.check(user, Role.ADMIN);
    Validator.checkIsNull(request, "request");
    return new CreateProjectResponse(
        repository.projects().create(user.account(), request.name(), request.description()));
  }

  @GET
  public GetProjectsResponse get(@Auth User user) {
    Authoriser.check(user, Role.ADMIN);
    return new GetProjectsResponse(
        repository.projects().findAll(user.account()).stream()
            .map(GetProjectResponse::from)
            .collect(ImmutableSet.toImmutableSet()));
  }
}
