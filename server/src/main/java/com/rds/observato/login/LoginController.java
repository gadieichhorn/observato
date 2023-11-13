package com.rds.observato.login;

import com.rds.observato.api.persistence.Repository;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("/login")
@Produces(MediaType.TEXT_HTML)
public record LoginController(Repository repository) {

  @GET
  public LoginView getPerson() {
    return new LoginView();
  }
}
