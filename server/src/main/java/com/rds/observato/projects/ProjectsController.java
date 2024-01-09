package com.rds.observato.projects;

import com.codahale.metrics.annotation.Timed;
import com.rds.observato.auth.Authoriser;
import com.rds.observato.auth.Role;
import com.rds.observato.auth.User;
import com.rds.observato.db.Repository;
import com.rds.observato.validation.Validator;
import com.rds.observato.view.ProjectsView;
import io.dropwizard.auth.Auth;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;

@Timed
@Path("projects")
@Produces({MediaType.APPLICATION_JSON, MediaType.TEXT_HTML})
public record ProjectsController(Repository repository) {

  @POST
  public CreateProjectResponse post(@Auth User user, CreateProjectRequest request) {
    Authoriser.check(user, Role.ADMIN);
    Validator.checkIsNull(request, "request");
    return new CreateProjectResponse(
        repository.projects().create(user.account(), request.name(), request.description()));
  }

  @GET
  public ProjectsView getProjects(@Auth User user) throws InterruptedException {
    Authoriser.check(user, Role.ADMIN);
    Thread.sleep(100);
    return new ProjectsView(repository.projects().findAll(user.account()));
  }
}
