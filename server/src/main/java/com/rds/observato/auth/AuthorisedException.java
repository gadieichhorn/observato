package com.rds.observato.auth;

import java.util.Arrays;

public class AuthorisedException extends RuntimeException {
  private final Roles[] requiredRoles;

  public AuthorisedException(Roles[] requiredRoles) {
    this.requiredRoles = requiredRoles;
  }

  @Override
  public String toString() {
    return "AuthorisedException{" + "requiredRoles=" + Arrays.toString(requiredRoles) + '}';
  }
}
