package com.rds.observato.resources;

import java.util.Set;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.statement.GetGeneratedKeys;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;

public interface ResourcesDao {
  @SqlUpdate(
      """
                  insert into obs.resources (account_id, name) values(:account, :name)
                  """)
  @GetGeneratedKeys
  long create(@Bind("account") long account, @Bind("name") String name);

  @SqlQuery(
      """
              select id,account_id, name from obs.resources where account_id = :account
              """)
  Set<ResourceView> getAll(@Bind("account") long account);
}
