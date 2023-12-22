package com.rds.observato.view;

import com.rds.observato.auth.Authoriser;
import com.rds.observato.auth.Role;
import com.rds.observato.auth.User;
import com.rds.observato.db.Repository;
import io.dropwizard.auth.Auth;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("resources/view/{id}")
@Produces(MediaType.TEXT_HTML)
public record ResourceViewController(Repository repository) {

  @GET
  public ResourceView view(@Auth User user, @PathParam("id") long id) {
    Authoriser.check(user, Role.ADMIN);
    return repository.resources().findById(user.account(), id).map(ResourceView::new).orElseThrow();
  }
}
