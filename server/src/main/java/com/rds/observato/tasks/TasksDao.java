package com.rds.observato.tasks;

import java.util.Optional;
import java.util.Set;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.statement.GetGeneratedKeys;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;

public interface TasksDao {
  @SqlUpdate(
      """
                      insert into obs.tasks (account_id, name, description, skills) values(:account, :name, :description, '{}')
              """)
  @GetGeneratedKeys
  long create(
      @Bind("account") long account,
      @Bind("name") String name,
      @Bind("description") String description);

  @SqlQuery(
      """
                    select id, revision,  account_id , name , description, skills  from obs.tasks where account_id = :account
              """)
  Set<TaskView> findAll(@Bind("account") long account);

  @SqlQuery(
      """
                  select t.id, t.revision,  t.account_id, t.name, t.description, t.skills
                  from obs.project_tasks pt
                           join obs.tasks t on pt.task_id = t.id
                  where t.account_id = :account
                    and pt.project_id = :project
                                    """)
  Set<TaskView> findAllByProject(@Bind("account") long account, @Bind("project") long project);

  @SqlQuery(
      """
                    select id, revision, account_id, name, description, skills  from obs.tasks where account_id = :account and id = :id
              """)
  Optional<TaskView> finById(@Bind("account") long account, @Bind("id") long id);

  @SqlUpdate(
      """
              update obs.tasks
              set name= :name, description = :description, revision = revision + 1
              where account_id = :account
                and id = :id
                and revision = :revision;
                      """)
  //  @GetGeneratedKeys
  int updateTask(
      @Bind("account") long account,
      @Bind("id") long id,
      @Bind("revision") int revision,
      @Bind("name") String name,
      @Bind("description") String description);
}
