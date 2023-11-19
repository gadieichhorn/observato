package com.rds.observato.tasks;

import java.util.Set;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.statement.GetGeneratedKeys;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;

public interface TasksDao {
  @SqlUpdate(
      """
                      insert into obs.tasks (account_id, name, description) values(:account, :name, :description)
              """)
  @GetGeneratedKeys
  long create(
      @Bind("account") long account,
      @Bind("name") String name,
      @Bind("description") String description);

  @SqlQuery(
      """
                    select id, revision,  account_id , name , description  from obs.tasks where account_id = :account
              """)
  Set<TaskView> findAll(@Bind("account") long account);

  @SqlQuery(
      """
                  select t.id, t.revision,  t.account_id, t.name, t.description
                  from obs.project_tasks pt
                           join obs.tasks t on pt.task_id = t.id
                  where t.account_id = :account
                    and pt.project_id = :project
                                    """)
  Set<TaskView> findAllByProject(@Bind("account") long account, @Bind("project") long project);
}
