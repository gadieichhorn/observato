package com.rds.observato.view;

import com.rds.observato.auth.Authoriser;
import com.rds.observato.auth.Role;
import com.rds.observato.auth.User;
import com.rds.observato.db.Repository;
import io.dropwizard.auth.Auth;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("projects/view")
@Produces(MediaType.TEXT_HTML)
public record ProjectsViewController(Repository repository) {

  @GET
  public ProjectsView getProjects(@Auth User user) {
    Authoriser.check(user, Role.ADMIN);
    return new ProjectsView(repository.projects().findAll(user.account()));
  }
}
