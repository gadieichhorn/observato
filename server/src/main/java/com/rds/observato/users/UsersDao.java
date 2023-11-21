package com.rds.observato.users;

import java.util.Optional;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.statement.GetGeneratedKeys;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;

public interface UsersDao {

  @SqlUpdate(
      """
              insert into obs.users (name, salt, secret) values(:name,:salt, :secret)
              """)
  @GetGeneratedKeys
  long create(@Bind("name") String name, @Bind("salt") byte[] salt, @Bind("secret") byte[] secret);

  @SqlQuery("""
          select id, revision, name from obs.users where id = :id
          """)
  Optional<UserView> findById(@Bind("id") long id);

  @SqlQuery(
      """
          select id, revision, name, salt, secret from obs.users where name = :name
          """)
  Optional<LoginView> findByName(@Bind("name") String name);

  //  @SqlQuery(
  //      """
  //              select id, revision, name, salt, secret from obs.users where name = :name
  //              """)
  //  Set<UserRoleView> userRolesByAccount(@Bind("user") long user, @Bind("account") long account);
}
