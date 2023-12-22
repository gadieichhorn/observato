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

@Path("tasks/view")
@Produces(MediaType.TEXT_HTML)
public record TasksViewController(Repository repository) {

  @GET
  public TasksView getProjects(@Auth User user) {
    Authoriser.check(user, Role.ADMIN);
    return new TasksView(repository.tasks().findAll(user.account()));
  }
}
