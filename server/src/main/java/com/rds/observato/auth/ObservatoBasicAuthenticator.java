package com.rds.observato.auth;

import com.rds.observato.api.persistence.Repository;
import io.dropwizard.auth.AuthenticationException;
import io.dropwizard.auth.Authenticator;
import java.util.Optional;

public record ObservatoBasicAuthenticator(Repository repository, AuthService auth)
    implements Authenticator<String, User> {
  @Override
  public Optional<User> authenticate(String token) throws AuthenticationException {
    //    if ("secret".equals(credentials.getPassword())) {
    //      return Optional.of(new User(credentials.getUsername(), ImmutableSet.of()));
    //    }
    return Optional.empty();
  }
}
