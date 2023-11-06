package com.rds.observato.server.controller;

import com.rds.observato.api.persistence.Repository;
import com.rds.observato.api.request.CreateAccountRequest;
import com.rds.observato.api.response.CreateAccountResponse;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.WebApplicationException;

@Path("accounts")
public record AccountsController(Repository repository) {

  @POST
  public CreateAccountResponse create(CreateAccountRequest request) {
    return repository
        .createAccount(request)
        .map(CreateAccountResponse::new)
        .getOrElseThrow(() -> new WebApplicationException("Failed to create Account"));
  }

  //
  //  @Override
  //  public void getAll(@NotNull Context context) {
  //    context.result("boom");
  //  }
  //
}
