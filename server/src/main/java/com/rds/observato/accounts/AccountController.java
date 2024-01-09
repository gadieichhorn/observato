package com.rds.observato.accounts;

import com.codahale.metrics.annotation.Timed;
import com.rds.observato.auth.Authoriser;
import com.rds.observato.auth.Role;
import com.rds.observato.auth.User;
import com.rds.observato.db.Repository;
import com.rds.observato.view.AccountView;
import io.dropwizard.auth.Auth;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;

@Timed
@Path("accounts/{id}")
@Produces({MediaType.APPLICATION_JSON, MediaType.TEXT_HTML})
public record AccountController(Repository repository) {
  
  @GET
  public AccountView getProject(@Auth User user, @PathParam("id") long id) {
    Authoriser.check(user, Role.ADMIN);
    return repository.accounts().findById(id).map(AccountView::new).orElseThrow();
  }
}
