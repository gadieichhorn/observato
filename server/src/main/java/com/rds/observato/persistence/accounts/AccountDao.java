package com.rds.observato.persistence.accounts;

import java.util.Optional;
import java.util.Set;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.statement.GetGeneratedKeys;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;

public interface AccountDao {

  @SqlUpdate(
      """
              insert into obs.accounts (name, owner_id) values(:name, :owner)
              """)
  @GetGeneratedKeys
  long create(@Bind("name") String name, @Bind("owner") long owner);

  @SqlQuery("""
          select * from obs.accounts where id = :id
          """)
  Optional<AccountView> findById(@Bind("id") long id);

  @SqlQuery("""
          select id,name,owner_id from obs.accounts
          """)
  Set<AccountView> getAll();

  @SqlQuery("""
          select id,name,owner_id from obs.accounts
          """)
  Set<AccountView> getAllAccountByUser(@Bind("user") long user);

  @SqlUpdate(
      """
          insert into obs.account_users (user_id, account_id, role) values ( :user, :account, 'ADMIN')
          """)
  void assignUserToAccount(@Bind("user") long user, @Bind("account") long account);

  @SqlQuery(
      """
          select user_id,account_id, role from obs.account_users where  user_id = :user
          """)
  Set<UserAccountView> getAccountByUser(@Bind("user") long user);

  @SqlQuery(
      """
          select user_id,account_id, role from obs.account_users where  account_id = :account
          """)
  Set<UserAccountView> getUsersByAccount(@Bind("account") long account);
}
