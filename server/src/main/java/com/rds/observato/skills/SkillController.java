package com.rds.observato.skills;

import com.codahale.metrics.annotation.Timed;
import com.rds.observato.Repository;
import com.rds.observato.auth.Authoriser;
import com.rds.observato.auth.Role;
import com.rds.observato.auth.User;
import io.dropwizard.auth.Auth;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;

@Timed
@Path("skills/{id}")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public record SkillController(Repository repository) {

  @GET
  public GetSkillResponse get(@Auth User user, @PathParam("id") long id) {
    Authoriser.check(user, Role.ADMIN);
    return null;
    //    new GetSkillsResponse(
    //        repository.skills().findAll(user.account()).stream()
    //            .map(GetSkillResponse::from)
    //            .collect(ImmutableSet.toImmutableSet()));
  }
}
