package com.rds.observato.db;

import com.rds.observato.resources.ResourceView;
import java.util.Optional;
import java.util.Set;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.locator.UseClasspathSqlLocator;
import org.jdbi.v3.sqlobject.statement.GetGeneratedKeys;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;

@UseClasspathSqlLocator
public interface ResourcesDao {
  @SqlUpdate
  @GetGeneratedKeys
  long create(@Bind("account") long account, @Bind("name") String name);

  @SqlQuery
  Set<ResourceView> findAll(@Bind("account") long account);

  @SqlQuery
  Optional<ResourceView> findById(@Bind("account") long account, @Bind("resource") long resource);
}
