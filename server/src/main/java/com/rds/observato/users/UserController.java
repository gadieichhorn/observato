package com.rds.observato.users;

import com.codahale.metrics.annotation.Timed;
import com.rds.observato.Repository;
import com.rds.observato.auth.Authoriser;
import com.rds.observato.auth.Role;
import com.rds.observato.auth.User;
import io.dropwizard.auth.Auth;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Timed
@Path("users/{id}")
@Produces(MediaType.APPLICATION_JSON)
public record UserController(Repository repository) {
  private static final Logger log = LoggerFactory.getLogger(UserController.class);

  @GET
  public Optional<UserView> getOne(@Auth User user, @PathParam("id") long id) {
    Authoriser.check(user, Role.ADMIN);
    log.info("ID: {}", id);
    return repository.users().findById(id);
  }
}
