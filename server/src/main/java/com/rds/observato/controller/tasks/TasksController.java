package com.rds.observato.controller.tasks;

import com.codahale.metrics.annotation.Timed;
import com.rds.observato.api.persistence.Repository;
import com.rds.observato.api.request.CreateTaskRequest;
import com.rds.observato.api.response.CreateTaskResponse;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Timed
@Path("tasks/{account}")
@Produces(MediaType.APPLICATION_JSON)
public record TasksController(Repository repository) {

  @POST
  public CreateTaskResponse create(@PathParam("account") long account, CreateTaskRequest request) {
    return new CreateTaskResponse(
        repository.tasks().create(account, request.name(), request.description()));
  }
}
