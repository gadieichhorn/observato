package com.rds.observato.auth;

import com.google.common.collect.ImmutableSet;
import com.rds.observato.api.persistence.Repository;
import io.dropwizard.auth.AuthenticationException;
import io.dropwizard.auth.Authenticator;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public record ObservatoBasicAuthenticator(Repository repository)
    implements Authenticator<String, User> {

  private static final Logger log = LoggerFactory.getLogger(ObservatoBasicAuthenticator.class);

  @Override
  public Optional<User> authenticate(String token) throws AuthenticationException {
    repository.accounts().getUserToken(token).ifPresent(view -> log.info("TOKEN: {}", view));
    if ("secret".equals(token)) {
      return Optional.of(new User("gadi@rds.com", ImmutableSet.of(Roles.ADMIN)));
    }
    return Optional.empty();
  }
}
