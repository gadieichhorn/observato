package com.rds.observato.resources;

import com.rds.observato.api.persistence.Repository;
import com.rds.observato.auth.User;
import io.dropwizard.auth.Auth;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;

@Path("resources/{account}/{resource}")
public record ResourceController(Repository repository) {

  @GET
  public GetResourceResponse create(@Auth User user,
      @PathParam("account") long account, @PathParam("resource") long resource) {
    return repository
        .resources()
        .findById(account, resource)
        .map(GetResourceResponse::from)
        .orElseThrow(() -> new RuntimeException("Not found"));
  }
}
