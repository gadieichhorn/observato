package com.rds.observato.controller.users;

import com.codahale.metrics.annotation.Timed;
import com.rds.observato.api.persistence.Repository;
import com.rds.observato.persistence.users.UserView;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import java.util.Optional;

@Timed
@Path("users/{id}")
@Produces(MediaType.APPLICATION_JSON)
public record UserController(Repository repository) {

  @GET
  public Optional<UserView> getOne(@PathParam("id") long id) {
    return repository.users().findById(id);
  }
}
