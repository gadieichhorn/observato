package com.rds.observato.db;

import com.rds.observato.projects.ProjectView;
import java.util.Optional;
import java.util.Set;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.locator.UseClasspathSqlLocator;
import org.jdbi.v3.sqlobject.statement.GetGeneratedKeys;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;

@UseClasspathSqlLocator
public interface ProjectsDao {

  @SqlUpdate
  @GetGeneratedKeys
  long create(
      @Bind("account") long account,
      @Bind("name") String name,
      @Bind("description") String description);

  @SqlQuery
  Optional<ProjectView> findById(@Bind("account") long account, @Bind("id") long id);

  @SqlQuery
  Set<ProjectView> findAll(@Bind("account") long account);

  @SqlUpdate()
  void assignTaskToProject(
      @Bind("account") long account, @Bind("task") long task, @Bind("project") long project);
}
