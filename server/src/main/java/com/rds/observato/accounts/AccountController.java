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
import java.util.Optional;

@Timed
@Path("accounts/{id}")
@Produces(MediaType.APPLICATION_JSON)
public record AccountController(Repository repository) {

  @GET
  public Optional<GetAccountResponse> get(@Auth User user, @PathParam("id") long id) {
    Authoriser.check(user, Role.ADMIN);
    Validator.checkIsNullOrNegative(id, "id");
    return repository.accounts().findById(id).map(GetAccountResponse::from);
  }
}
