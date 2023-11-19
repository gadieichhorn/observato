package com.rds.observato.projects;

import com.rds.observato.api.persistence.Repository;
import com.rds.observato.api.response.ProjectResponse;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import java.util.Optional;

@Path("projects/{account}/{project}")
public record ProjectController(Repository repository) {

  private static ProjectConverter converter = new ProjectConverter();

  @GET
  public Optional<ProjectResponse> get(
      @PathParam("account") long account, @PathParam("project") long project) {
    return repository.projects().findById(account, project).map(converter::convert);
  }
}
