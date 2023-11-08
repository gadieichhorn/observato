package com.rds.observato.auth;

import com.google.common.collect.ImmutableSet;
import com.rds.observato.api.persistence.Repository;
import io.dropwizard.auth.AuthenticationException;
import io.dropwizard.auth.Authenticator;
import io.dropwizard.auth.basic.BasicCredentials;
import java.util.Optional;

public record ObservatoBasicAuthenticator(Repository repository, AuthService auth)
    implements Authenticator<BasicCredentials, User> {
  @Override
  public Optional<User> authenticate(BasicCredentials credentials) throws AuthenticationException {
    if ("secret".equals(credentials.getPassword())) {
      return Optional.of(new User(credentials.getUsername(), ImmutableSet.of()));
    }
    return Optional.empty();
  }
}
