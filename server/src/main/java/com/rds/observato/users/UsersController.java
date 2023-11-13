package com.rds.observato.users;

import com.codahale.metrics.annotation.Timed;
import com.rds.observato.api.persistence.Repository;
import com.rds.observato.api.request.CreateUserRequest;
import com.rds.observato.api.response.CreateUserResponse;
import com.rds.observato.auth.AuthService;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

@Timed
@Path("users")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public record UsersController(Repository repository, AuthService auth) {
  @POST
  public CreateUserResponse create(CreateUserRequest request) {
    try {
      byte[] salt = auth.salt();
      byte[] hash = auth.hash(salt, request.password());
      return new CreateUserResponse(repository.users().create(request.name(), salt, hash));
    } catch (NoSuchAlgorithmException | InvalidKeySpecException ex) {
      throw new WebApplicationException("Failed to create user", 500);
    }
  }
}
