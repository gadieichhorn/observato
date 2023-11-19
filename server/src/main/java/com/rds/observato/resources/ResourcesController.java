package com.rds.observato.resources;

import com.google.common.collect.ImmutableSet;
import com.rds.observato.api.persistence.Repository;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;

@Path("resources/{account}")
public record ResourcesController(Repository repository) {

  @POST
  public CreateResourceResponse create(
      @PathParam("account") long account, CreateResourceRequest request) {
    return new CreateResourceResponse(repository.resources().create(account, request.name()));
  }

  @GET
  public GetResourcesResponse get(@PathParam("account") long account) {
    return new GetResourcesResponse(
        repository.resources().getAll(account).stream()
            .map(GetResourceResponse::from)
            .collect(ImmutableSet.toImmutableSet()));
  }
}
