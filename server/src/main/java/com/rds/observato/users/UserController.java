package com.rds.observato.users;

import com.codahale.metrics.annotation.Timed;
import com.rds.observato.auth.Authoriser;
import com.rds.observato.auth.Role;
import com.rds.observato.auth.User;
import com.rds.observato.db.Repository;
import com.rds.observato.view.UserView;
import io.dropwizard.auth.Auth;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Timed
@Path("users/{id}")
@Produces({MediaType.APPLICATION_JSON, MediaType.TEXT_HTML})
public record UserController(Repository repository) {

  @GET
  public UserView get(@Auth User user, @PathParam("id") long id) {
    Authoriser.check(user, Role.ADMIN);
    return repository
        .users()
        .findById(id)
        .map(UserView::new)
        .orElseThrow(
            () -> new WebApplicationException("User not found", Response.Status.NOT_FOUND));
  }
}
