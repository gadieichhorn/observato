package com.rds.observato.persistence.users;

import com.rds.observato.model.User;

public record UserView(long id, String name) implements User {

  public User to() {
    return new UserView(id, name);
  }
}
