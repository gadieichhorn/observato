package com.rds.observato.auth;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;
import com.rds.observato.validation.Validator;
import java.security.Principal;
import java.util.Set;

public record User(String username, Set<String> roles) implements Principal {

  public static final User ANONYMOUS = new User("anonymous@rds.com", ImmutableSet.of());

  public User {
    Validator.checkIsNullOrEmpty(username, "username");
  }

  @Override
  public String getName() {
    return username;
  }

  public boolean hasAnyOf(String... requiredRoles) {
    return !Sets.intersection(roles, ImmutableSet.copyOf(requiredRoles)).isEmpty();
  }
}
