package com.rds.observato.projects;

import com.codahale.metrics.annotation.Timed;
import com.rds.observato.auth.Authoriser;
import com.rds.observato.auth.Role;
import com.rds.observato.auth.User;
import com.rds.observato.db.Repository;
import com.rds.observato.view.ProjectView;
import io.dropwizard.auth.Auth;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Timed
@Path("projects/{project}")
@Produces({MediaType.APPLICATION_JSON, MediaType.TEXT_HTML})
public record ProjectController(Repository repository) {

  @GET
  public ProjectView getProject(@Auth User user, @PathParam("project") long project) {
    Authoriser.check(user, Role.ADMIN);
    return repository
        .projects()
        .findById(user.account(), project)
        .map(ProjectView::new)
        .orElseThrow(
            () -> new WebApplicationException("Project not found", Response.Status.NOT_FOUND));
  }
}
