package com.rds.observato.auth;

import java.util.Arrays;

public class AuthorisedException extends RuntimeException {
  private final Role[] requiredRoles;

  public AuthorisedException(Role[] requiredRoles) {
    this.requiredRoles = requiredRoles;
  }

  @Override
  public String toString() {
    return "AuthorisedException{" + "requiredRoles=" + Arrays.toString(requiredRoles) + '}';
  }
}
