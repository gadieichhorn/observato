package com.rds.observato.resources;

import java.util.Optional;
import java.util.Set;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.statement.GetGeneratedKeys;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;

public interface ResourcesDao {
  @SqlUpdate(
      """
                  insert into obs.resources (account_id, name, skills) values(:account, :name, '{}')
                  """)
  @GetGeneratedKeys
  long create(@Bind("account") long account, @Bind("name") String name);

  @SqlQuery(
      """
              select id, revision, account_id, name, skills from obs.resources where account_id = :account
              """)
  Set<ResourceView> getAll(@Bind("account") long account);

  @SqlQuery(
      """
              select id, revision, account_id, name, skills
              from obs.resources
              where account_id = :account
              and id = :resource
              """)
  Optional<ResourceView> findById(@Bind("account") long account, @Bind("resource") long resource);
}
