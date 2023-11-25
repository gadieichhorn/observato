package com.rds.observato.accounts;

import com.codahale.metrics.annotation.Timed;
import com.rds.observato.auth.Authoriser;
import com.rds.observato.auth.Role;
import com.rds.observato.auth.User;
import com.rds.observato.db.Repository;
import com.rds.observato.validation.Validator;
import io.dropwizard.auth.Auth;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;

@Timed
@Path("accounts")
@Produces(MediaType.APPLICATION_JSON)
public record AccountsController(Repository repository) {

  @POST
  public CreateAccountResponse post(@Auth User user, CreateAccountRequest request) {
    Authoriser.check(user, Role.ADMIN);
    Validator.checkIsNull(request, "request");
    return new CreateAccountResponse(repository.accounts().create(request.name(), request.owner()));
  }

  //  @GET
  //  public GetAccountsResponse get(@Auth User user, long account) {
  //    Authoriser.check(user, Roles.ADMIN);
  //    return new GetAccountsResponse(
  //        repository.accounts().findById(account).stream()
  //            .map(GetAccountResponse::from)
  //            .collect(Collectors.toSet()));
  //  }
}
