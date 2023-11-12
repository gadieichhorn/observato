package com.rds.observato.controller.login;

import com.rds.observato.views.login.LoginView;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("/login")
@Produces(MediaType.TEXT_HTML)
public class LoginController {

  @GET
  public LoginView getPerson() {
    return new LoginView();
  }
}
