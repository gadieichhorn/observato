package com.rds.observato.auth;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;
import com.rds.observato.validation.Validator;
import java.security.Principal;
import java.util.Set;

public record User(long id, long account, String username, Set<Role> roles) implements Principal {

  public User {
    Validator.checkIsNull(roles, "roles");
    Validator.checkIsNullOrNegative(id, "id");
    Validator.checkIsNullOrEmpty(username, "username");
    Validator.checkIsNullOrNegative(account, "account");
  }

  @Override
  public String getName() {
    return username;
  }

  public boolean hasAnyOf(Role... requiredRoles) {
    return !Sets.intersection(roles, ImmutableSet.copyOf(requiredRoles)).isEmpty();
  }
}
