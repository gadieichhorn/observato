package com.rds.observato.schedule;

import com.codahale.metrics.annotation.Timed;
import com.rds.observato.auth.Authoriser;
import com.rds.observato.auth.Role;
import com.rds.observato.auth.User;
import com.rds.observato.db.Repository;
import com.rds.observato.view.ScheduleView;
import io.dropwizard.auth.Auth;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Timed
@Path("schedule")
@Produces({MediaType.APPLICATION_JSON, MediaType.TEXT_HTML})
public record ScheduleController(Repository repository) {

  @GET
  public ScheduleView get(@Auth User user) {
    Authoriser.check(user, Role.ADMIN);
    return new ScheduleView();
  }
}
