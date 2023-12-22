package com.rds.observato.db;

import com.rds.observato.accounts.AccountRecord;
import com.rds.observato.accounts.TokenView;
import com.rds.observato.accounts.UserAccountView;
import com.rds.observato.auth.Role;
import java.util.Optional;
import java.util.Set;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.locator.UseClasspathSqlLocator;
import org.jdbi.v3.sqlobject.statement.GetGeneratedKeys;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;

@UseClasspathSqlLocator
public interface AccountsDao {

  @SqlUpdate
  @GetGeneratedKeys
  long create(@Bind("name") String name, @Bind("owner") long owner);

  @SqlQuery
  Optional<AccountRecord> findById(@Bind("id") long id);

  @SqlUpdate
  void assignUserToAccount(
      @Bind("user") long user, @Bind("account") long account, @Bind("role") Role role);

  @SqlQuery
  Set<UserAccountView> getAccountByUser(@Bind("user") long user, @Bind("account") long account);

  @SqlQuery
  Set<UserAccountView> getAccountsByUser(@Bind("user") long user);

  @SqlQuery
  Set<UserAccountView> getUsersByAccount(@Bind("account") long account);

  @SqlQuery
  Optional<TokenView> getUserToken(@Bind("token") String token);

  @SqlUpdate
  @GetGeneratedKeys
  long createUserTokenForAccount(
      @Bind("user") long user, @Bind("account") long account, @Bind("token") String token);
}
