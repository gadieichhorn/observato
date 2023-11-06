package com.rds.observato.persistence;

import com.rds.observato.persistence.view.AccountView;
import java.util.Optional;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.statement.GetGeneratedKeys;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;

public interface AccountDao {

  @SqlUpdate(
      """
              insert into accounts.accounts (name, owner) values(:name, :owner)
              """)
  @GetGeneratedKeys
  long create(@Bind("name") String name, @Bind("owner") String owner);

  @SqlQuery("""
          select * from accounts.accounts where id = :id
          """)
  Optional<AccountView> findById(@Bind("id") long id);
}
