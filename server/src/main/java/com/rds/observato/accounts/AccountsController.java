package com.rds.observato.accounts;

import com.codahale.metrics.annotation.Timed;
import com.rds.observato.api.persistence.Repository;
import com.rds.observato.api.request.CreateAccountRequest;
import com.rds.observato.api.response.CreateAccountResponse;
import com.rds.observato.api.response.GetAccountsResponse;
import com.rds.observato.auth.Authoriser;
import com.rds.observato.auth.Roles;
import com.rds.observato.auth.User;
import io.dropwizard.auth.Auth;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import java.util.stream.Collectors;

@Timed
@Path("accounts")
@Produces(MediaType.APPLICATION_JSON)
public record AccountsController(Repository repository) {

  @POST
  public CreateAccountResponse post(@Auth User user, CreateAccountRequest request) {
    Authoriser.check(user, Roles.ADMIN);
    return new CreateAccountResponse(repository.accounts().create(request.name(), request.owner()));
  }

  @GET
  public GetAccountsResponse get(@Auth User user) {
    Authoriser.check(user, Roles.ADMIN);
    return new GetAccountsResponse(
        repository.accounts().getAccountsByUser(3).stream()
            .map(GetAccountResponse::from)
            .collect(Collectors.toSet()));
  }
}
