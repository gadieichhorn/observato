package com.rds.observato.db;

import com.rds.observato.assignments.AssignmentView;
import java.time.Instant;
import java.util.Optional;
import java.util.Set;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.locator.UseClasspathSqlLocator;
import org.jdbi.v3.sqlobject.statement.GetGeneratedKeys;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;

@UseClasspathSqlLocator
public interface AssignmentDao {

  @SqlUpdate
  @GetGeneratedKeys
  long create(
      @Bind("account") long account,
      @Bind("task") long task,
      @Bind("resource") long resource,
      @Bind("start") Instant start,
      @Bind("end") Instant end);

  @SqlQuery
  Set<AssignmentView> findAll(@Bind("account") long account);

  @SqlQuery
  Optional<AssignmentView> findById(@Bind("account") long account, @Bind("id") long id);
}
