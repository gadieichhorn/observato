package com.rds.observato.persistence;

import com.rds.observato.DatabaseTestBase;
import com.rds.observato.api.persistence.Repository;
import com.rds.observato.api.request.CreateAccountRequest;
import java.sql.SQLException;
import javax.sql.DataSource;
import org.assertj.vavr.api.VavrAssertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RepositoryDaoTest extends DatabaseTestBase {

  private Repository repository;

  @BeforeEach
  void setUp() throws SQLException {
    DataSource datasource = datasource();
    migrate(datasource);
    repository = RepositoryDao.create(jdbi(datasource.getConnection()));
  }

  @Test
  void create() {
    VavrAssertions.assertThat(
            repository.createAccount(new CreateAccountRequest("hilla", "hilla@observato.com")))
        .containsRightInstanceOf(Long.class);
  }

  @Test
  void duplicate() {
    repository.createAccount(new CreateAccountRequest("gadi", "gadi@observato.com"));
    VavrAssertions.assertThat(
            repository.createAccount(new CreateAccountRequest("gadi", "gadi@observato.com")))
        .isLeft()
        .containsLeftInstanceOf(SqlError.class);
    //        .containsOnLeft(new SqlError("blah"));
  }
}
