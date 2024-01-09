package com.rds.observato.accounts;

import com.codahale.metrics.annotation.Timed;
import com.rds.observato.auth.Authoriser;
import com.rds.observato.auth.Role;
import com.rds.observato.auth.User;
import com.rds.observato.db.Repository;
import com.rds.observato.validation.Validator;
import com.rds.observato.view.AccountsView;
import io.dropwizard.auth.Auth;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;

@Timed
@Path("accounts")
@Produces({MediaType.APPLICATION_JSON, MediaType.TEXT_HTML})
public record AccountsController(Repository repository) {

  @POST
  public CreateAccountResponse post(@Auth User user, CreateAccountRequest request) {
    Authoriser.check(user, Role.ADMIN);
    Validator.checkIsNull(request, "request");
    return new CreateAccountResponse(repository.accounts().create(request.name(), request.owner()));
  }

  @GET
  public AccountsView getProjects(@Auth User user) throws InterruptedException {
    Authoriser.check(user, Role.ADMIN);
    Thread.sleep(1000);
    return new AccountsView(repository.accounts().getAccountsByUser(user.id()));
  }
}
