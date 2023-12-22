package com.rds.observato.db;

import com.rds.observato.resources.ResourceRecord;
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
  Set<ResourceRecord> findAll(@Bind("account") long account);

  @SqlQuery
  Optional<ResourceRecord> findById(@Bind("account") long account, @Bind("resource") long resource);
}
