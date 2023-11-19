package com.rds.observato.projects;

import com.google.common.collect.ImmutableSet;
import com.rds.observato.api.persistence.Repository;
import com.rds.observato.api.response.GetProjectsResponse;
import com.rds.observato.api.response.ProjectResponse;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;

@Path("projects/{account}")
public record ProjectsController(Repository repository) {

  @GET
  public GetProjectsResponse getAll(@PathParam("account") long account) {
    return new GetProjectsResponse(
        repository.projects().findAll(account).stream()
            .map(ProjectResponse::from)
            .collect(ImmutableSet.toImmutableSet()));
  }
}
