package com.rds.observato.auth;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;
import com.rds.observato.validation.Validator;
import java.security.Principal;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public record User(long id, String username, Set<Role> roles) implements Principal {
  private static final Logger log = LoggerFactory.getLogger(User.class);

  public User {
    Validator.checkIsNullOrEmpty(username, "username");
  }

  @Override
  public String getName() {
    return username;
  }

  public boolean hasAnyOf(Role... requiredRoles) {
    log.info("USER ROLES: {}", roles);
    return !Sets.intersection(roles, ImmutableSet.copyOf(requiredRoles)).isEmpty();
  }
}
