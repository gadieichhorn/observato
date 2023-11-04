package com.rds.observato.schema;

import com.rds.observato.api.request.CreateAccountRequest;
import org.jdbi.v3.sqlobject.customizer.BindMethods;
import org.jdbi.v3.sqlobject.statement.GetGeneratedKeys;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;

public interface AccountDao {

  @SqlUpdate("""
          insert into accounts.accounts (name, owner) values(:name, :owner)
          """)
  @GetGeneratedKeys
  long create(@BindMethods CreateAccountRequest request);
}
