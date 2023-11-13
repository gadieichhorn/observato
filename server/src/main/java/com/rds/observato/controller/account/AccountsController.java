package com.rds.observato.controller.account;

import com.codahale.metrics.annotation.Timed;
import com.rds.observato.api.persistence.Repository;
import com.rds.observato.api.request.CreateAccountRequest;
import com.rds.observato.api.response.CreateAccountResponse;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;

@Timed
@Path("accounts")
@Produces(MediaType.APPLICATION_JSON)
public record AccountsController(Repository repository) {

  @POST
  public CreateAccountResponse create(CreateAccountRequest request) {
    return new CreateAccountResponse(repository.accounts().create(request.name(), request.owner()));
  }

  //  @GET
  //  public GetAccountsResponse getAll() {
  //    return new GetAccountsResponse(
  //
  // repository.accounts().getAccountByUser(0l).stream().map(AccountView::to).collect(Collectors.toSet()));
  //  }
}
