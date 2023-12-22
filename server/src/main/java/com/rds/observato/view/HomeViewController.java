package com.rds.observato.view;

import com.rds.observato.auth.User;
import com.rds.observato.db.Repository;
import io.dropwizard.auth.Auth;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("home/view")
@Produces(MediaType.TEXT_HTML)
public record HomeViewController(Repository repository) {

  @GET
  public HomeView getProject(@Auth User user) {
    //    Authoriser.check(user, Role.ADMIN);
    //    return repository.projects().findById(user.account(),
    // id).map(ProjectView::new).orElseThrow();
    return new HomeView();
  }
}
