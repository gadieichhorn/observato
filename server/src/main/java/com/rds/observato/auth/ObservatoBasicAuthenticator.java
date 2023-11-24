package com.rds.observato.auth;

import com.rds.observato.api.persistence.Repository;
import io.dropwizard.auth.AuthenticationException;
import io.dropwizard.auth.Authenticator;
import java.util.Optional;

public record ObservatoBasicAuthenticator(Repository repository)
    implements Authenticator<String, User> {

  @Override
  public Optional<User> authenticate(String token) throws AuthenticationException {
    return repository
        .accounts()
        .getUserToken(token)
        .map(view -> new User(view.user(), view.account(), view.username(), view.roles()));
  }
}
