package com.rds.observato.persistence;

import com.rds.observato.DatabaseTestBase;
import com.rds.observato.api.persistence.Repository;
import java.sql.SQLException;
import org.assertj.core.api.Assertions;
import org.jdbi.v3.core.statement.UnableToExecuteStatementException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RepositoryDaoTest extends DatabaseTestBase {

  private Repository repository;

  @BeforeEach
  void setUp() throws SQLException {
    repository = RepositoryDao.create(jdbi());
  }

  @Test
  void create() {
    Assertions.assertThat(repository.accounts().create("hilla", "hilla@observato.com"))
        .isGreaterThan(0);
  }

  @Test
  void duplicate() {
    repository.accounts().create("gadi", "gadi@observato.com");
    Assertions.assertThatThrownBy(() -> repository.accounts().create("gadi", "gadi@observato.com"))
        .isInstanceOf(UnableToExecuteStatementException.class);
  }
}
