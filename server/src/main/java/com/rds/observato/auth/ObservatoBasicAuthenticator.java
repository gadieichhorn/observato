package com.rds.observato.auth;

import com.google.common.collect.ImmutableSet;
import com.rds.observato.accounts.TokenView;
import com.rds.observato.accounts.UserAccountView;
import com.rds.observato.api.persistence.Repository;
import com.rds.observato.users.UserView;
import io.dropwizard.auth.AuthenticationException;
import io.dropwizard.auth.Authenticator;
import java.util.Optional;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public record ObservatoBasicAuthenticator(Repository repository)
    implements Authenticator<String, User> {

  private static final Logger log = LoggerFactory.getLogger(ObservatoBasicAuthenticator.class);

  @Override
  public Optional<User> authenticate(String token) throws AuthenticationException {
    TokenView view =
        repository.accounts().getUserToken(token).orElse(new TokenView(0, 0, 0, "", null));
    log.info("TOKEN: {}", view);
    if (view.id() > 0) {
      UserView user =
          repository.users().findById(view.user()).orElse(new UserView(0, 0, "anonymous"));
      log.info("USER: {}", user);
      // TODO load user account role
      Set<UserAccountView> userRoleViews =
          repository.accounts().getAccountByUser(view.user(), view.account());
      log.info("ROLES: {}", userRoleViews);

      ImmutableSet<Roles> roles =
          userRoleViews.stream().map(UserAccountView::role).collect(ImmutableSet.toImmutableSet());

      return Optional.of(new User("gadi@rds.com", roles));
    } else {
      return Optional.of(new User("anonymous@rds.com", ImmutableSet.of(Roles.ANONYMOUS)));
    }
  }
}
