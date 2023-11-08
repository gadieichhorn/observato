// package com.rds.observato.auth;
//
// import io.dropwizard.auth.AuthFilter;
// import jakarta.annotation.Priority;
// import jakarta.ws.rs.Priorities;
// import jakarta.ws.rs.WebApplicationException;
// import jakarta.ws.rs.container.ContainerRequestContext;
// import jakarta.ws.rs.core.Response;
// import java.security.Principal;
//
// @Priority(Priorities.AUTHENTICATION)
// public class ObservatoAuthFilter<P extends Principal>
//    extends AuthFilter<String, P> {
//
//  @Override
//  public void filter(ContainerRequestContext containerRequestContext) {
//    String jwtString =
// containerRequestContext.getHeaders().getFirst("x-auth-request-access-token");
//    if (!authenticate(containerRequestContext, jwtString, "")) {
//      throw new WebApplicationException(Response.Status.UNAUTHORIZED);
//    }
//  }
//
//  public static class Builder<P extends Principal>
//      extends AuthFilterBuilder<String, P, ObservatoAuthFilter<P>> {
//    public Builder() {}
//
//    protected ObservatoAuthFilter<P> newInstance() {
//      return new ObservatoAuthFilter<P>();
//    }
//  }
// }
