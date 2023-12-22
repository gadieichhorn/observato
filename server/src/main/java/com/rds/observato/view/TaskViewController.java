package com.rds.observato.view;

import com.rds.observato.auth.Authoriser;
import com.rds.observato.auth.Role;
import com.rds.observato.auth.User;
import com.rds.observato.db.Repository;
import io.dropwizard.auth.Auth;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("tasks/view/{id}")
@Produces(MediaType.TEXT_HTML)
public record TaskViewController(Repository repository) {

  @GET
  public TaskView getProject(@Auth User user, @PathParam("id") long id) {
    Authoriser.check(user, Role.ADMIN);
    return repository.tasks().finById(user.account(), id).map(TaskView::new).orElseThrow();
  }
}
