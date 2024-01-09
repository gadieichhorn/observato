package com.rds.observato.resources;

import com.rds.observato.auth.Authoriser;
import com.rds.observato.auth.Role;
import com.rds.observato.auth.User;
import com.rds.observato.db.Repository;
import com.rds.observato.view.ResourceView;
import io.dropwizard.auth.Auth;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Response;

@Path("resources/{resource}")
public record ResourceController(Repository repository) {

  @GET
  public ResourceView view(@Auth User user, @PathParam("resource") long resource) {
    Authoriser.check(user, Role.ADMIN);
    return repository
        .resources()
        .findById(user.account(), resource)
        .map(ResourceView::new)
        .orElseThrow(
            () -> new WebApplicationException("Resource not found", Response.Status.NOT_FOUND));
  }
}
