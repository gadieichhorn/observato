package com.rds.observato.resources;

import com.rds.observato.auth.Authoriser;
import com.rds.observato.auth.Role;
import com.rds.observato.auth.User;
import com.rds.observato.db.Repository;
import com.rds.observato.view.ResourcesView;
import io.dropwizard.auth.Auth;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("resources")
@Produces({MediaType.TEXT_HTML, MediaType.APPLICATION_JSON})
public record ResourcesController(Repository repository) {

  @POST
  public CreateResourceResponse create(@Auth User user, CreateResourceRequest request) {
    Authoriser.check(user, Role.ADMIN);
    return new CreateResourceResponse(
        repository.resources().create(user.account(), request.name()));
  }

  @GET
  public ResourcesView view(@Auth User user) {
    Authoriser.check(user, Role.ADMIN);
    return new ResourcesView(repository.resources().findAll(user.account()));
  }
}
