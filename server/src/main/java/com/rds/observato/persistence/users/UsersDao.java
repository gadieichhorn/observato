package com.rds.observato.persistence.users;

import java.util.Optional;
import java.util.Set;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.statement.GetGeneratedKeys;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;

public interface UsersDao {

  @SqlUpdate(
      """
              insert into users (name, salt, secret) values(:name,:salt, :secret)
              """)
  @GetGeneratedKeys
  long create(@Bind("name") String name, @Bind("salt") byte[] salt, @Bind("secret") byte[] secret);

  @SqlQuery("""
          select id, name from users where id = :id
          """)
  Optional<UserView> findById(@Bind("id") long id);

  @SqlQuery("""
          select id, name, salt, secret from users where name = :name
          """)
  Optional<LoginView> findByName(@Bind("name") String name);

  @SqlQuery(
      """
              select id, name, salt, secret from users where name = :name
              """)
  Set<UserRoleView> userRoleByAccount(@Bind("user") String user, @Bind("account") long account);
}
