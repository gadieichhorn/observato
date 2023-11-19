package com.rds.observato.accounts;

import com.codahale.metrics.annotation.Timed;
import com.rds.observato.api.persistence.Repository;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import java.util.Optional;

@Timed
@Path("accounts/{id}")
@Produces(MediaType.APPLICATION_JSON)
public record AccountController(Repository repository) {

  @GET
  public Optional<GetAccountResponse> getOne(@PathParam("id") long id) {
    return repository.accounts().findById(id).map(GetAccountResponse::from);
  }
}
