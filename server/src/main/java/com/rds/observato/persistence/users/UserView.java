package com.rds.observato.persistence.users;

import com.rds.observato.api.model.Account;
import com.rds.observato.model.User;
import org.eclipse.jetty.server.Authentication;

public record UserView(long id, String name) implements User {

  public User to() {
    return new UserView(id, name);
  }
}
