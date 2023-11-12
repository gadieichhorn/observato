package com.rds.observato.controller.projects;

import com.google.common.collect.ImmutableSet;
import com.rds.observato.api.persistence.Repository;
import com.rds.observato.api.response.ProjectsResponse;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;

@Path("projects/{account}")
public record ProjectsController(Repository repository) {

  @GET
  public ProjectsResponse getAll(@PathParam("account") long account) {
    // TODO access validation
    ProjectConverter converter = new ProjectConverter();
    return new ProjectsResponse(
        repository.projects().findAll(account).stream()
            .map(converter::convert)
            .collect(ImmutableSet.toImmutableSet()));
  }
}
