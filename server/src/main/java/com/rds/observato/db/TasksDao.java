package com.rds.observato.db;

import com.rds.observato.tasks.TaskView;
import java.util.Optional;
import java.util.Set;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.locator.UseClasspathSqlLocator;
import org.jdbi.v3.sqlobject.statement.GetGeneratedKeys;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;

@UseClasspathSqlLocator
public interface TasksDao {
  @SqlUpdate
  @GetGeneratedKeys
  long create(
      @Bind("account") long account,
      @Bind("name") String name,
      @Bind("description") String description);

  @SqlQuery
  Set<TaskView> findAll(@Bind("account") long account);

  @SqlQuery
  Set<TaskView> findAllByProject(@Bind("account") long account, @Bind("project") long project);

  @SqlQuery
  Optional<TaskView> finById(@Bind("account") long account, @Bind("id") long id);

  @SqlUpdate
  int update(
      @Bind("account") long account,
      @Bind("id") long id,
      @Bind("revision") int revision,
      @Bind("name") String name,
      @Bind("description") String description);
}
