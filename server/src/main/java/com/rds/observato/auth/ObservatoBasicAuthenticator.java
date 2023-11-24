package com.rds.observato.auth;

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
    return repository
        .accounts()
        .getUserToken(token)
        .map(view -> new User(view.user(), view.account(), view.username(), view.roles()));
  }
}
