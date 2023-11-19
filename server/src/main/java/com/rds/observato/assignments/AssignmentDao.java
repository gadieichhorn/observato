package com.rds.observato.assignments;

import java.time.Instant;
import java.util.Optional;
import java.util.Set;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.statement.GetGeneratedKeys;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;

public interface AssignmentDao {

  @SqlUpdate(
      """
              insert into obs.assignments
              (account_id, task_id , resource_id, start_time, end_time)
              values(:account, :task, :resource, :start, :end)
              """)
  @GetGeneratedKeys
  long create(
      @Bind("account") long account,
      @Bind("task") long task,
      @Bind("resource") long resource,
      @Bind("start") Instant start,
      @Bind("end") Instant end);

  @SqlQuery(
      """
              select id, revision, account_id, task_id, resource_id, start_time, end_time
              from obs.assignments
              where account_id = :account
              """)
  Set<AssignmentView> getAll(@Bind("account") long account);

  @SqlQuery(
      """
                  select id, revision, account_id, task_id, resource_id, start_time, end_time
                  from obs.assignments
                  where account_id = :account and id = :id
                  """)
  Optional<AssignmentView> findById(@Bind("account") long account, @Bind("id") long id);
}
