package com.rds.observato.auth;

import io.dropwizard.auth.AuthFilter;
import jakarta.annotation.Priority;
import jakarta.ws.rs.Priorities;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.core.Response;

@Priority(Priorities.AUTHENTICATION)
public class ObservatoAuthFilter extends AuthFilter<String, User> {

  @Override
  public void filter(ContainerRequestContext containerRequestContext) {
    String token = containerRequestContext.getHeaders().getFirst("observato-api-token");
    if (!authenticate(containerRequestContext, token, "")) {
      throw new WebApplicationException(Response.Status.UNAUTHORIZED);
    }
  }

  public static class Builder
      extends AuthFilterBuilder<String, com.rds.observato.auth.User, ObservatoAuthFilter> {
    public Builder() {}

    protected ObservatoAuthFilter newInstance() {
      return new ObservatoAuthFilter();
    }
  }
}
