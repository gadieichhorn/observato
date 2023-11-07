package com.rds.observato.controller.users;

import com.codahale.metrics.annotation.Timed;
import com.rds.observato.api.persistence.Repository;
import com.rds.observato.model.User;
import com.rds.observato.persistence.users.UserView;
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
  public Optional<User> getOne(@PathParam("id") long id) {
    return repository.users().findById(id).map(UserView::to);
  }
}
