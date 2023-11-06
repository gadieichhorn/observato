package com.rds.observato.persistence;

import com.rds.observato.DatabaseTestBase;
import com.rds.observato.persistence.view.AccountView;
import java.sql.SQLException;
import javax.sql.DataSource;
import org.assertj.core.api.Assertions;
import org.jdbi.v3.core.statement.UnableToExecuteStatementException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class AccountDaoTest extends DatabaseTestBase {

  private AccountDao dao;

  @BeforeEach
  void setUp() throws SQLException {
    DataSource datasource = datasource();
    migrate(datasource);
    dao = jdbi(datasource.getConnection()).onDemand(AccountDao.class);
  }

  @Test
  void create() {
    Assertions.assertThatCode(() -> dao.create("account1", "hilla@observato.com"))
        .doesNotThrowAnyException();
  }

  @Test
  void duplicate() {
    dao.create("account2", "gadi@observato.com");
    Assertions.assertThatThrownBy(() -> dao.create("account2", "gadi@observato.com"))
        .isInstanceOf(UnableToExecuteStatementException.class);
  }

  @Test
  void createAndGet() {
    long id = dao.create("account3", "gadi@observato.com");
    Assertions.assertThat(dao.findById(id))
        .isPresent()
        .get()
        .isInstanceOf(AccountView.class)
        .hasFieldOrPropertyWithValue("name", "account3")
        .hasFieldOrPropertyWithValue("owner", "gadi@observato.com");
  }

  @Test
  void notFound() {
    Assertions.assertThat(dao.findById(0)).isEmpty();
  }
}
