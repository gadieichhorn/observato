package com.rds.observato.server.controller;

import com.rds.observato.api.model.Account;
import com.rds.observato.api.persistence.Repository;
import com.rds.observato.persistence.view.AccountView;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.WebApplicationException;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Path("accounts/{id}")
public record AccountController(Repository repository) {

  private static final Logger log = LoggerFactory.getLogger(AccountController.class);

  //  @Override
  //  public void delete(@NotNull Context context, @NotNull String s) {}
  //
  @GET
  public Optional<Account> getOne(@PathParam("id") Long id) {
    return repository
        .findAccountById(id)
        .map(a -> a.map(AccountView::to))
        .peekLeft(error -> log.warn("Error: {}", error))
        .getOrElseThrow(() -> new WebApplicationException("Not found"));
  }

  //  @Override
  //  public void update(@NotNull Context context, @NotNull String s) {}
}
