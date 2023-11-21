package com.rds.observato.auth;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;
import com.rds.observato.validation.Validator;
import java.security.Principal;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public record User(String username, Set<Roles> roles) implements Principal {
  private static final Logger log = LoggerFactory.getLogger(User.class);
  public static final User ANONYMOUS = new User("anonymous@rds.com", ImmutableSet.of());

  public User {
    Validator.checkIsNullOrEmpty(username, "username");
  }

  @Override
  public String getName() {
    return username;
  }

  public boolean hasAnyOf(Roles... requiredRoles) {
    log.info("USER ROLES: {}", roles);
    return !Sets.intersection(roles, ImmutableSet.copyOf(requiredRoles)).isEmpty();
  }
}
