package com.rds.observato.resources;

import com.google.common.collect.ImmutableSet;
import com.rds.observato.api.persistence.Repository;
import com.rds.observato.auth.Authoriser;
import com.rds.observato.auth.Roles;
import com.rds.observato.auth.User;
import io.dropwizard.auth.Auth;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;

@Path("resources/{account}")
public record ResourcesController(Repository repository) {

  @POST
  public CreateResourceResponse create(
      @Auth User user, @PathParam("account") long account, CreateResourceRequest request) {
    Authoriser.check(user, Roles.ADMIN);
    return new CreateResourceResponse(repository.resources().create(account, request.name()));
  }

  @GET
  public GetResourcesResponse get(@Auth User user, @PathParam("account") long account) {
    Authoriser.check(user, Roles.ADMIN);
    return new GetResourcesResponse(
        repository.resources().getAll(account).stream()
            .map(GetResourceResponse::from)
            .collect(ImmutableSet.toImmutableSet()));
  }
}
