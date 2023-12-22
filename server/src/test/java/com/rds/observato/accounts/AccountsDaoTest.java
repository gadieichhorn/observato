package com.rds.observato.accounts;

import static org.assertj.core.api.Assertions.*;

import com.rds.observato.DatabaseTestBase;
import com.rds.observato.Fixtures;
import com.rds.observato.auth.Role;
import org.jdbi.v3.core.statement.UnableToExecuteStatementException;
import org.junit.jupiter.api.Test;
import org.testcontainers.shaded.com.google.common.collect.ImmutableSet;

class AccountsDaoTest extends DatabaseTestBase {

  private final long user = Fixtures.createUser(repository);
  private final String token = Fixtures.token(user);

  @Test
  void create() {
    assertThatCode(() -> repository.accounts().create("a010", user)).doesNotThrowAnyException();
  }

  @Test
  void duplicate() {
    repository.accounts().create("a011", user);
    assertThatThrownBy(() -> repository.accounts().create("a011", user))
        .isInstanceOf(UnableToExecuteStatementException.class);
  }

  @Test
  void createAndGet() {
    long id = repository.accounts().create("a013", user);
    assertThat(repository.accounts().findById(id))
        .isPresent()
        .get()
        .isInstanceOf(AccountRecord.class)
        .hasFieldOrPropertyWithValue("name", "a013")
        .hasFieldOrPropertyWithValue("owner", user);
  }

  @Test
  void notFound() {
    assertThat(repository.accounts().findById(0)).isEmpty();
  }

  @Test
  void assignUserToAccount() {
    long account = repository.accounts().create("a0001", user);
    repository.accounts().assignUserToAccount(user, account, Role.ADMIN);
  }

  @Test
  void token() {
    long account = repository.accounts().create("a0008", user);
    repository.accounts().createUserTokenForAccount(user, account, token);
    repository.accounts().assignUserToAccount(user, account, Role.ADMIN);
    repository.accounts().assignUserToAccount(user, account, Role.RESOURCE);
    assertThat(repository.accounts().getUserToken(token))
        .isPresent()
        .get()
        .hasFieldOrPropertyWithValue("user", user)
        .hasFieldOrPropertyWithValue("roles", ImmutableSet.of(Role.ADMIN, Role.RESOURCE))
        .hasFieldOrPropertyWithValue("account", account);
  }

  @Test
  void assignDuplicateUserToAccount() {
    long account = repository.accounts().create("a0002", user);
    repository.accounts().assignUserToAccount(user, account, Role.ADMIN);
    assertThatThrownBy(() -> repository.accounts().assignUserToAccount(user, account, Role.ADMIN))
        .isInstanceOf(UnableToExecuteStatementException.class);
  }

  @Test
  void getAccountForUser() {
    long user1 = repository.users().create("u003", "salt".getBytes(), "hash".getBytes());
    long user2 = repository.users().create("u004", "salt".getBytes(), "hash".getBytes());
    long account1 = repository.accounts().create("a0003", user);
    long account2 = repository.accounts().create("a0004", user);
    long account3 = repository.accounts().create("a0005", user);

    repository.accounts().assignUserToAccount(user1, account1, Role.ADMIN);
    repository.accounts().assignUserToAccount(user1, account2, Role.SCHEDULER);
    repository.accounts().assignUserToAccount(user2, account3, Role.RESOURCE);

    assertThat(repository.accounts().getAccountByUser(user1, account1))
        .containsExactlyInAnyOrder(new UserAccountView(user1, account1, Role.ADMIN));

    assertThat(repository.accounts().getAccountByUser(user1, account2))
        .containsExactlyInAnyOrder(new UserAccountView(user1, account2, Role.SCHEDULER));

    assertThat(repository.accounts().getAccountByUser(user2, account3))
        .containsExactlyInAnyOrder(new UserAccountView(user2, account3, Role.RESOURCE));
  }

  @Test
  void getUsersForAccount() {
    long user1 = repository.users().create("u007", "salt".getBytes(), "hash".getBytes());
    long user2 = repository.users().create("u008", "salt".getBytes(), "hash".getBytes());
    long user3 = repository.users().create("u009", "salt".getBytes(), "hash".getBytes());
    long account1 = repository.accounts().create("a0006", user1);
    long account2 = repository.accounts().create("a0007", user);
    repository.accounts().assignUserToAccount(user1, account1, Role.ADMIN);
    repository.accounts().assignUserToAccount(user2, account1, Role.SCHEDULER);
    repository.accounts().assignUserToAccount(user3, account2, Role.RESOURCE);
    assertThat(repository.accounts().getUsersByAccount(account1))
        .containsExactlyInAnyOrder(
            new UserAccountView(user1, account1, Role.ADMIN),
            new UserAccountView(user2, account1, Role.SCHEDULER));
    assertThat(repository.accounts().getUsersByAccount(account2))
        .containsExactlyInAnyOrder(new UserAccountView(user3, account2, Role.RESOURCE));
  }
}
