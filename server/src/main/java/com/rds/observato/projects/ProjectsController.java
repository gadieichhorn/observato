package com.rds.observato.projects;

import com.codahale.metrics.annotation.Timed;
import com.google.common.collect.ImmutableSet;
import com.rds.observato.api.persistence.Repository;
import com.rds.observato.api.response.GetProjectResponse;
import com.rds.observato.api.response.GetProjectsResponse;
import com.rds.observato.auth.Authoriser;
import com.rds.observato.auth.Role;
import com.rds.observato.auth.User;
import com.rds.observato.validation.Validator;
import io.dropwizard.auth.Auth;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Timed
@Path("projects/{account}")
@Produces(MediaType.APPLICATION_JSON)
public record ProjectsController(Repository repository) {

  private static final Logger log = LoggerFactory.getLogger(ProjectsController.class);

  @POST
  public CreateProjectResponse post(
      @Auth User user, @PathParam("account") Long account, CreateProjectRequest request) {
    log.info("USER:{}", user);
    Authoriser.check(user, Role.ADMIN);
    Validator.checkIsNullOrNegative(account, "account");
    Validator.checkIsNull(request, "request");
    return new CreateProjectResponse(
        repository.projects().create(account, request.name(), request.description()));
  }

  @GET
  public GetProjectsResponse get(@Auth User user, @PathParam("account") long account) {
    Authoriser.check(user, Role.ADMIN);
    Validator.checkIsNullOrNegative(account, "account");
    return new GetProjectsResponse(
        repository.projects().findAll(account).stream()
            .map(GetProjectResponse::from)
            .collect(ImmutableSet.toImmutableSet()));
  }
}
