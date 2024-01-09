package com.rds.observato.home;

import com.rds.observato.auth.Authoriser;
import com.rds.observato.auth.Role;
import com.rds.observato.auth.User;
import com.rds.observato.db.Repository;
import com.rds.observato.view.HomeView;
import io.dropwizard.auth.Auth;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("home")
@Produces({MediaType.TEXT_HTML, MediaType.APPLICATION_JSON})
public record HomeViewController(Repository repository) {

  @GET
  public HomeView getProject(@Auth User user) {
    Authoriser.check(user, Role.ADMIN);
    return new HomeView();
  }
}
