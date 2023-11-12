package com.rds.observato.persistence.accounts;

import java.util.Optional;
import java.util.Set;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.statement.GetGeneratedKeys;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;

public interface AccountDao {

  @SqlUpdate(
      """
              insert into obs.accounts (name, owner) values(:name, :owner)
              """)
  @GetGeneratedKeys
  long create(@Bind("name") String name, @Bind("owner") String owner);

  @SqlQuery("""
          select * from obs.accounts where id = :id
          """)
  Optional<AccountView> findById(@Bind("id") long id);

  @SqlQuery("""
          select id,name,owner from obs.accounts
          """)
  Set<AccountView> getAll();

  @SqlQuery("""
          select id,name,owner from obs.accounts
          """)
  Set<AccountView> getAllAccountByUser(@Bind("user") long user);
}
