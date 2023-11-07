package com.rds.observato.persistence;

import com.rds.observato.DatabaseTestBase;
import com.rds.observato.api.persistence.Repository;
import com.rds.observato.persistence.users.UserView;
import java.sql.SQLException;
import javax.sql.DataSource;
import org.assertj.core.api.Assertions;
import org.jdbi.v3.core.statement.UnableToExecuteStatementException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class UsersDaoTest extends DatabaseTestBase {

  private Repository repository;

  @BeforeEach
  void setUp() throws SQLException {
    DataSource datasource = datasource();
    migrate(datasource);
    repository = RepositoryDao.create(jdbi(datasource.getConnection()));
  }

  @Test
  void create() {
    Assertions.assertThatCode(
            () ->
                repository
                    .users()
                    .create("account1", "hilla@observato.com".getBytes(), "secret".getBytes()))
        .doesNotThrowAnyException();
  }

  @Test
  void duplicate() {
    repository.users().create("account2", "gadi@observato.com".getBytes(), "secret".getBytes());
    Assertions.assertThatThrownBy(
            () ->
                repository
                    .users()
                    .create("account2", "gadi@observato.com".getBytes(), "secret".getBytes()))
        .isInstanceOf(UnableToExecuteStatementException.class);
  }

  @Test
  void createAndGet() {
    long id =
        repository.users().create("account3", "gadi@observato.com".getBytes(), "secret".getBytes());
    Assertions.assertThat(repository.users().findById(id))
        .isPresent()
        .get()
        .isInstanceOf(UserView.class)
        .hasFieldOrPropertyWithValue("name", "account3");
  }
}
