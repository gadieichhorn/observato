package com.rds.observato.auth;

import io.dropwizard.auth.Authorizer;
import jakarta.ws.rs.container.ContainerRequestContext;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ObservatoAuthorizer implements Authorizer<User> {

  private static final Logger log = LoggerFactory.getLogger(ObservatoAuthorizer.class);

  @Override
  public boolean authorize(
      User principal, String role, @Nullable ContainerRequestContext requestContext) {
    log.info("USER: {}", principal);
    return principal.getName().equals("good-guy") && role.equals("ADMIN");
  }
}
