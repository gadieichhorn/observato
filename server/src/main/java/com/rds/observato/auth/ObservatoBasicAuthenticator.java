package com.rds.observato.auth;

import com.google.common.collect.ImmutableSet;
import com.rds.observato.api.persistence.Repository;
import io.dropwizard.auth.AuthenticationException;
import io.dropwizard.auth.Authenticator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

public record ObservatoBasicAuthenticator(Repository repository, AuthService auth)
    implements Authenticator<String, User> {

  private static final Logger log = LoggerFactory.getLogger(ObservatoBasicAuthenticator.class);
  @Override
  public Optional<User> authenticate(String token) throws AuthenticationException {
    log.info("TOKEN: {}", token);
        if ("secret".equals(token)) {
          return Optional.of(new User("gadi@rds.com", ImmutableSet.of()));
        }
    return Optional.empty();
  }
}
