package com.rds.observato.projects;

import java.util.Optional;
import java.util.Set;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.statement.GetGeneratedKeys;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;

public interface ProjectsDao {

  @SqlUpdate(
      """
              insert into obs.projects (account_id, name, description) values(:account, :name, :description)
              """)
  @GetGeneratedKeys
  long create(
      @Bind("account") long account,
      @Bind("name") String name,
      @Bind("description") String description);

  @SqlQuery(
      """
                      select id , account_id , name , description  from obs.projects where account_id = :account and id=:id
              """)
  Optional<ProjectView> findById(@Bind("account") long account, @Bind("id") long id);

  @SqlQuery(
      """
                          select id , account_id , name , description  from obs.projects where account_id = :account
                  """)
  Set<ProjectView> findAll(@Bind("account") long account);

  @SqlUpdate(
      """
                          insert into obs.project_tasks (account_id, project_id, task_id) values(:account, :project, :task)
                  """)
  void assignTaskToProject(
      @Bind("account") long account, @Bind("task") long task, @Bind("project") long project);
}
