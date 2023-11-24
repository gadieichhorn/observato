package com.rds.observato.skills;

import com.codahale.metrics.annotation.Timed;
import com.rds.observato.Repository;
import com.rds.observato.auth.Authoriser;
import com.rds.observato.auth.Role;
import com.rds.observato.auth.User;
import com.rds.observato.validation.Validator;
import io.dropwizard.auth.Auth;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;

@Timed
@Path("skills")
@Produces(MediaType.APPLICATION_JSON)
public record SkillsController(Repository repository) {

  @POST
  public CreateSkillResponse post(@Auth User user, CreateSkillRequest request) {
    Authoriser.check(user, Role.ADMIN);
    Validator.checkIsNull(request, "request");
    return new CreateSkillResponse(
        repository.skills().create(user.account(), request.name(), request.description()));
  }
}
