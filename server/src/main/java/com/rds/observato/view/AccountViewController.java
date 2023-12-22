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

@Path("accounts/view/{id}")
@Produces(MediaType.TEXT_HTML)
public record AccountViewController(Repository repository) {

  @GET
  public AccountView getProject(@Auth User user, @PathParam("id") long id) {
    Authoriser.check(user, Role.ADMIN);
    return repository.accounts().findById(id).map(AccountView::new).orElseThrow();
  }
}
