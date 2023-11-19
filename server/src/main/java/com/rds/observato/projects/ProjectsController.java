package com.rds.observato.projects;

import com.google.common.collect.ImmutableSet;
import com.rds.observato.api.persistence.Repository;
import com.rds.observato.api.response.GetProjectResponse;
import com.rds.observato.api.response.GetProjectsResponse;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;

@Path("projects/{account}")
public record ProjectsController(Repository repository) {

  @POST
  public CreateProjectResponse create(
      @PathParam("account") long account, CreateProjectRequest request) {
    return new CreateProjectResponse(
        repository.projects().create(account, request.name(), request.description()));
  }

  @GET
  public GetProjectsResponse getAll(@PathParam("account") long account) {
    return new GetProjectsResponse(
        repository.projects().findAll(account).stream()
            .map(GetProjectResponse::from)
            .collect(ImmutableSet.toImmutableSet()));
  }
}
