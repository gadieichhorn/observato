package com.rds.observato.resources;

import com.rds.observato.Repository;
import com.rds.observato.auth.Authoriser;
import com.rds.observato.auth.Role;
import com.rds.observato.auth.User;
import io.dropwizard.auth.Auth;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;

@Path("resources/{resource}")
public record ResourceController(Repository repository) {

  @GET
  public GetResourceResponse create(@Auth User user, @PathParam("resource") long resource) {
    Authoriser.check(user, Role.ADMIN);
    return repository
        .resources()
        .findById(user.account(), resource)
        .map(GetResourceResponse::from)
        .orElseThrow(() -> new RuntimeException("Not found"));
  }
}
