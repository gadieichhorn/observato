package com.rds.observato.projects;

import com.rds.observato.api.persistence.Repository;
import com.rds.observato.api.response.GetProjectResponse;
import com.rds.observato.auth.User;
import io.dropwizard.auth.Auth;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("projects/{account}/{project}")
@Produces(MediaType.APPLICATION_JSON)
public record ProjectController(Repository repository) {

  @GET
  public GetProjectResponse get(@Auth User user,
      @PathParam("account") long account, @PathParam("project") long project) {
    return repository
        .projects()
        .findById(account, project)
        .map(GetProjectResponse::from)
        .orElseThrow(() -> new RuntimeException("Not found"));
  }
}
