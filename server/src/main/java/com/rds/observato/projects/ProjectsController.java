package com.rds.observato.projects;

import com.google.common.collect.ImmutableSet;
import com.rds.observato.api.persistence.Repository;
import com.rds.observato.api.response.GetProjectResponse;
import com.rds.observato.api.response.GetProjectsResponse;
import com.rds.observato.auth.Authoriser;
import com.rds.observato.auth.Role;
import com.rds.observato.auth.User;
import io.dropwizard.auth.Auth;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;

@Path("projects/{account}")
@Produces(MediaType.APPLICATION_JSON)
public record ProjectsController(Repository repository) {

  @POST
  public CreateProjectResponse post(
      @Auth User user, @PathParam("account") long account, CreateProjectRequest request) {
    Authoriser.check(user, Role.ADMIN);
    return new CreateProjectResponse(
        repository.projects().create(account, request.name(), request.description()));
  }

  @GET
  public GetProjectsResponse get(@Auth User user, @PathParam("account") long account) {
    Authoriser.check(user, Role.ADMIN);
    return new GetProjectsResponse(
        repository.projects().findAll(account).stream()
            .map(GetProjectResponse::from)
            .collect(ImmutableSet.toImmutableSet()));
  }
}
