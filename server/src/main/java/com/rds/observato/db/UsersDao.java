package com.rds.observato.db;

import com.rds.observato.users.LoginView;
import com.rds.observato.users.UserRecord;
import java.util.Optional;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.locator.UseClasspathSqlLocator;
import org.jdbi.v3.sqlobject.statement.GetGeneratedKeys;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;

@UseClasspathSqlLocator
public interface UsersDao {

  @SqlUpdate
  @GetGeneratedKeys
  long create(@Bind("name") String name, @Bind("salt") byte[] salt, @Bind("secret") byte[] secret);

  @SqlQuery
  Optional<UserRecord> findById(@Bind("id") long id);

  @SqlQuery
  Optional<LoginView> findByName(@Bind("name") String name);
}
