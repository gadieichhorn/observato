package com.rds.observato.controller.account;

import com.codahale.metrics.annotation.Timed;
import com.rds.observato.api.model.Account;
import com.rds.observato.api.persistence.Repository;
import com.rds.observato.auth.User;
import com.rds.observato.persistence.accounts.AccountView;
import io.dropwizard.auth.Auth;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import java.util.Optional;

@Timed
@Path("accounts/{id}")
@Produces(MediaType.APPLICATION_JSON)
public record AccountController(Repository repository) {

  @GET
  public Optional<Account> getOne(@Auth User user, @PathParam("id") Long id) {
    return repository.accounts().findById(id).map(AccountView::to);
  }
}
