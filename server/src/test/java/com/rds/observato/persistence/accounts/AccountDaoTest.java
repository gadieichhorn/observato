package com.rds.observato.persistence.accounts;

import static org.assertj.core.api.Assertions.*;

import com.rds.observato.DatabaseTestBase;
import com.rds.observato.api.persistence.Repository;
import com.rds.observato.persistence.RepositoryDao;
import java.sql.SQLException;
import java.util.UUID;
import org.jdbi.v3.core.statement.UnableToExecuteStatementException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class AccountDaoTest extends DatabaseTestBase {

  Repository repository;
  private long user;

  @BeforeEach
  void setUp() throws SQLException {
    repository = RepositoryDao.create(jdbi());
    user =
        repository
            .users()
            .create(UUID.randomUUID().toString(), "salt".getBytes(), "hash".getBytes());
  }

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
        .isInstanceOf(AccountView.class)
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
    repository.accounts().assignUserToAccount(user, account);
  }

  @Test
  void assignDuplicateUserToAccount() {
    long account = repository.accounts().create("a0002", user);
    repository.accounts().assignUserToAccount(user, account);
    assertThatThrownBy(() -> repository.accounts().assignUserToAccount(user, account))
        .isInstanceOf(UnableToExecuteStatementException.class);
  }

  @Test
  void getAccountForUser() {
    long user1 = repository.users().create("u003", "salt".getBytes(), "hash".getBytes());
    long user2 = repository.users().create("u004", "salt".getBytes(), "hash".getBytes());
    long account1 = repository.accounts().create("a0003", user);
    long account2 = repository.accounts().create("a0004", user);
    long account3 = repository.accounts().create("a0005", user);
    repository.accounts().assignUserToAccount(user1, account1);
    repository.accounts().assignUserToAccount(user1, account2);
    repository.accounts().assignUserToAccount(user2, account3);
    assertThat(repository.accounts().getAccountByUser(user1))
        .containsExactlyInAnyOrder(
            new UserAccountView(user1, account1, "ADMIN"),
            new UserAccountView(user1, account2, "ADMIN"));
    assertThat(repository.accounts().getAccountByUser(user2))
        .containsExactlyInAnyOrder(new UserAccountView(user2, account3, "ADMIN"));
  }

  @Test
  void getUsersForAccount() {
    long user1 = repository.users().create("u007", "salt".getBytes(), "hash".getBytes());
    long user2 = repository.users().create("u008", "salt".getBytes(), "hash".getBytes());
    long user3 = repository.users().create("u009", "salt".getBytes(), "hash".getBytes());
    long account1 = repository.accounts().create("a0006", user1);
    long account2 = repository.accounts().create("a0007", user);
    repository.accounts().assignUserToAccount(user1, account1);
    repository.accounts().assignUserToAccount(user2, account1);
    repository.accounts().assignUserToAccount(user3, account2);
    assertThat(repository.accounts().getUsersByAccount(account1))
        .containsExactlyInAnyOrder(
            new UserAccountView(user1, account1, "ADMIN"),
            new UserAccountView(user2, account1, "ADMIN"));
    assertThat(repository.accounts().getUsersByAccount(account2))
        .containsExactlyInAnyOrder(new UserAccountView(user3, account2, "ADMIN"));
  }
}
