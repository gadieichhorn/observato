package com.rds.observato.auth;

import io.dropwizard.auth.Authorizer;
import jakarta.ws.rs.container.ContainerRequestContext;
import org.checkerframework.checker.nullness.qual.Nullable;

public class ObservatoAuthorizer implements Authorizer<User> {

  @Override
  public boolean authorize(
      User principal, String role, @Nullable ContainerRequestContext requestContext) {
    return principal.getName().equals("good-guy") && role.equals("ADMIN");
  }
}
