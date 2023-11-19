package com.rds.observato.projects;

import com.rds.observato.api.persistence.Repository;
import com.rds.observato.api.response.GetProjectResponse;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;

@Path("projects/{account}/{project}")
public record ProjectController(Repository repository) {

  @GET
  public GetProjectResponse get(
      @PathParam("account") long account, @PathParam("project") long project) {
    return repository
        .projects()
        .findById(account, project)
        .map(GetProjectResponse::from)
        .orElseThrow(() -> new RuntimeException("Not found"));
  }
}
