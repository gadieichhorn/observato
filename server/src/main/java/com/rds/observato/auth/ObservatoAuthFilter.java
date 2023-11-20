package com.rds.observato.auth;

import io.dropwizard.auth.AuthFilter;
import jakarta.annotation.Priority;
import jakarta.ws.rs.Priorities;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.core.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Priority(Priorities.AUTHENTICATION)
public class ObservatoAuthFilter extends AuthFilter<String, User> {
  private static final Logger log = LoggerFactory.getLogger(ObservatoAuthFilter.class);

  @Override
  public void filter(ContainerRequestContext containerRequestContext) {
    String token = containerRequestContext.getHeaders().getFirst("observato-api-token");
    log.info("TOKEN: {}", token);
    if (!authenticate(containerRequestContext, token, "")) {
      throw new WebApplicationException(Response.Status.UNAUTHORIZED);
    }
  }

  public static class Builder
      extends AuthFilterBuilder<String, User, ObservatoAuthFilter> {
    public Builder() {}

    protected ObservatoAuthFilter newInstance() {
      return new ObservatoAuthFilter();
    }
  }
}
