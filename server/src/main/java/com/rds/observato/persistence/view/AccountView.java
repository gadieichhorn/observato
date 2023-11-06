package com.rds.observato.persistence.view;

import com.rds.observato.api.model.Account;

public record AccountView(long id, String name, String owner) implements Account {

  public Account to() {
    return new AccountView(id, name, owner);
  }
}
