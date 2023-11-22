package com.rds.observato.auth;

import com.rds.observato.validation.Validator;

public record Authoriser() {

  public static void check(User user, Role... requiredRoles) {
    Validator.checkIsNull(user, "user");
    if (!user.hasAnyOf(requiredRoles)) {
      throw new AuthorisedException(requiredRoles);
    }
  }
}
