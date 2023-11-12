package com.rds.observato.persistence.accounts;

import com.rds.observato.api.model.Account;
import org.jdbi.v3.core.mapper.reflect.ColumnName;

public record AccountView(@ColumnName("id") long id, @ColumnName("name") String name, String owner)
    implements Account {

  public Account to() {
    return new AccountView(id, name, owner);
  }
}
