package com.rds.observato.persistence.projects;

import java.util.Optional;
import java.util.Set;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.statement.GetGeneratedKeys;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;

public interface ProjectsDao {

  @SqlUpdate(
      """
              insert into projects.projects (account_id, name, description) values(:account, :name, :description)
              """)
  @GetGeneratedKeys
  long create(
      @Bind("account") Long account,
      @Bind("name") String name,
      @Bind("description") String description);

  @SqlQuery(
      """
                      select id , account_id , name , description  from projects.projects where account_id = :account and name = :name
              """)
  Optional<ProjectView> findByName(@Bind("account") long account, @Bind("name") String name);

  @SqlQuery(
      """
                          select id , account_id , name , description  from projects.projects where account_id = :account
                  """)
  Set<ProjectView> findAll(@Bind("account") long account);
}
