package com.rds.observato.resources;

import com.google.common.collect.ImmutableSet;
import com.rds.observato.Repository;
import com.rds.observato.auth.Authoriser;
import com.rds.observato.auth.Role;
import com.rds.observato.auth.User;
import io.dropwizard.auth.Auth;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;

@Path("resources")
public record ResourcesController(Repository repository) {

  @POST
  public CreateResourceResponse create(@Auth User user, CreateResourceRequest request) {
    Authoriser.check(user, Role.ADMIN);
    return new CreateResourceResponse(
        repository.resources().create(user.account(), request.name()));
  }

  @GET
  public GetResourcesResponse get(@Auth User user) {
    Authoriser.check(user, Role.ADMIN);
    return new GetResourcesResponse(
        repository.resources().findAll(user.account()).stream()
            .map(GetResourceResponse::from)
            .collect(ImmutableSet.toImmutableSet()));
  }
}
