package com.rds.observato.persistence.accounts;

import static org.assertj.core.api.Assertions.*;

import com.rds.observato.DatabaseTestBase;
import com.rds.observato.api.persistence.Repository;
import com.rds.observato.persistence.RepositoryDao;
import com.rds.observato.persistence.users.UsersDao;
import java.sql.SQLException;
import org.jdbi.v3.core.statement.UnableToExecuteStatementException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class AccountDaoTest extends DatabaseTestBase {

  private AccountDao accountDao;
  private UsersDao usersDao;

  @BeforeEach
  void setUp() throws SQLException {
    Repository repository = RepositoryDao.create(jdbi());
    accountDao = repository.accounts();
    usersDao = repository.users();
  }

  @Test
  void create() {
    assertThatCode(() -> accountDao.create("account1", "hilla@observato.com"))
        .doesNotThrowAnyException();
  }

  @Test
  void duplicate() {
    accountDao.create("account2", "gadi@observato.com");
    assertThatThrownBy(() -> accountDao.create("account2", "gadi@observato.com"))
        .isInstanceOf(UnableToExecuteStatementException.class);
  }

  @Test
  void createAndGet() {
    long id = accountDao.create("account3", "gadi@observato.com");
    assertThat(accountDao.findById(id))
        .isPresent()
        .get()
        .isInstanceOf(AccountView.class)
        .hasFieldOrPropertyWithValue("name", "account3")
        .hasFieldOrPropertyWithValue("owner", "gadi@observato.com");
  }

  @Test
  void notFound() {
    assertThat(accountDao.findById(0)).isEmpty();
  }

  @Test
  void assignUserToAccount() {
    long user = usersDao.create("u001", "salt".getBytes(), "hash".getBytes());
    long account = accountDao.create("a0001", "account");
    accountDao.assignUserToAccount(user, account);
  }

  @Test
  void assignDuplicateUserToAccount() {
    long user = usersDao.create("u002", "salt".getBytes(), "hash".getBytes());
    long account = accountDao.create("a0002", "account");
    accountDao.assignUserToAccount(user, account);
    assertThatThrownBy(() -> accountDao.assignUserToAccount(user, account))
        .isInstanceOf(UnableToExecuteStatementException.class);
  }

  @Test
  void getAccountForUser() {
    long user1 = usersDao.create("u003", "salt".getBytes(), "hash".getBytes());
    long user2 = usersDao.create("u004", "salt".getBytes(), "hash".getBytes());
    long account1 = accountDao.create("a0003", "account");
    long account2 = accountDao.create("a0004", "account");
    long account3 = accountDao.create("a0005", "account");
    accountDao.assignUserToAccount(user1, account1);
    accountDao.assignUserToAccount(user1, account2);
    accountDao.assignUserToAccount(user2, account3);
    assertThat(accountDao.getAccountByUser(user1))
        .containsExactlyInAnyOrder(
            new UserAccountView(user1, account1, "ADMIN"),
            new UserAccountView(user1, account2, "ADMIN"));
    assertThat(accountDao.getAccountByUser(user2))
        .containsExactlyInAnyOrder(new UserAccountView(user2, account3, "ADMIN"));
  }

  @Test
  void getUsersForAccount() {
    long user1 = usersDao.create("u007", "salt".getBytes(), "hash".getBytes());
    long user2 = usersDao.create("u008", "salt".getBytes(), "hash".getBytes());
    long user3 = usersDao.create("u009", "salt".getBytes(), "hash".getBytes());
    long account1 = accountDao.create("a0006", "account");
    long account2 = accountDao.create("a0007", "account");
    accountDao.assignUserToAccount(user1, account1);
    accountDao.assignUserToAccount(user2, account1);
    accountDao.assignUserToAccount(user3, account2);
    assertThat(accountDao.getUsersByAccount(account1))
        .containsExactlyInAnyOrder(
            new UserAccountView(user1, account1, "ADMIN"),
            new UserAccountView(user2, account1, "ADMIN"));
    assertThat(accountDao.getUsersByAccount(account2))
        .containsExactlyInAnyOrder(new UserAccountView(user3, account2, "ADMIN"));
  }
}
