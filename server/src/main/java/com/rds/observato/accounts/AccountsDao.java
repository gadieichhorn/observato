package com.rds.observato.accounts;

import com.rds.observato.auth.Roles;
import java.util.Optional;
import java.util.Set;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.statement.GetGeneratedKeys;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;

public interface AccountsDao {

  @SqlUpdate(
      """
              insert into obs.accounts (name, owner_id)
              values(:name, :owner)
              """)
  @GetGeneratedKeys
  long create(@Bind("name") String name, @Bind("owner") long owner);

  @SqlQuery("""
          select * from obs.accounts
          where id = :id
          """)
  Optional<AccountView> findById(@Bind("id") long id);

  @SqlQuery(
      """
          select id, revision, name, owner_id
          from obs.accounts
          where owner_id = :user
          """)
  Set<AccountView> getAccountsByUser(@Bind("user") long user);

  @SqlUpdate(
      """
              insert into obs.account_users (user_id, account_id, role)
              values ( :user, :account, :role)
              """)
  void assignUserToAccount(
      @Bind("user") long user, @Bind("account") long account, @Bind("role") Roles role);

  @SqlQuery(
      """
              select user_id,account_id, role
              from obs.account_users
              where  user_id = :user
                and account_id = :account
              """)
  Set<UserAccountView> getAccountByUser(@Bind("user") long user, @Bind("account") long account);

  @SqlQuery(
      """
          select user_id,account_id, role
          from obs.account_users
          where  account_id = :account
          """)
  Set<UserAccountView> getUsersByAccount(@Bind("account") long account);

  @SqlQuery(
      """
                select id, user_id, account_id, token, created_on
                from obs.tokens
                where token = :token
          """)
  Optional<TokenView> getUserToken(@Bind("token") String token);

  @SqlUpdate(
      """
          insert into obs.tokens
          (user_id, account_id, token, created_on)
          values (:user,:account, :token, now())
          """)
  @GetGeneratedKeys
  long createUserTokenForAccount(
      @Bind("user") long user, @Bind("account") long account, @Bind("token") String token);
}
