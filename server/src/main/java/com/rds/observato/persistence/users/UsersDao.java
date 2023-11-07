package com.rds.observato.persistence.users;

import java.util.Optional;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.statement.GetGeneratedKeys;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;

public interface UsersDao {

  @SqlUpdate(
      """
              insert into users.users (name, salt, secret) values(:name,:salt, :secret)
              """)
  @GetGeneratedKeys
  long create(@Bind("name") String name, @Bind("salt") byte[] salt, @Bind("secret") byte[] secret);

  @SqlQuery("""
          select id, name from users.users where id = :id
          """)
  Optional<UserView> findById(@Bind("id") long id);

  @SqlQuery(
      """
          select id, name, salt, secret from users.users where name = :name
          """)
  Optional<LoginView> findByName(@Bind("name") String name);
}
