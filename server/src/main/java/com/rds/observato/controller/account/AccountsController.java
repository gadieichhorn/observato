package com.rds.observato.controller.account;

import com.codahale.metrics.annotation.Timed;
import com.rds.observato.api.persistence.Repository;
import com.rds.observato.api.request.CreateAccountRequest;
import com.rds.observato.api.response.CreateAccountResponse;
import com.rds.observato.api.response.GetAccountsResponse;
import com.rds.observato.persistence.accounts.AccountView;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import java.util.stream.Collectors;

@Timed
@Path("accounts")
@Produces(MediaType.APPLICATION_JSON)
public record AccountsController(Repository repository) {

  @POST
  public CreateAccountResponse create(CreateAccountRequest request) {
    return new CreateAccountResponse(repository.accounts().create(request.name(), request.owner()));
  }

  @GET
  public GetAccountsResponse getAll() {
    return new GetAccountsResponse(
        repository.accounts().getAll().stream().map(AccountView::to).collect(Collectors.toSet()));
  }
}
