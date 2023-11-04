package com.rds.observato.schema;

import com.rds.observato.api.model.Account;
import com.rds.observato.api.persistence.Repository;
import com.rds.observato.api.request.CreateAccountRequest;
import javax.sql.DataSource;
import org.assertj.vavr.api.VavrAssertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class RepositoryDaoTest extends DatabaseTestBase {

  private static final Logger log = LoggerFactory.getLogger(RepositoryDaoTest.class);

  private Repository repository;

  @BeforeEach
  void setUp() {
    DataSource datasource = datasource();
    migrate(datasource);
    repository = RepositoryDao.create(jdbi(datasource));
  }

  @Test
  void create() {
    VavrAssertions.assertThat(
            repository.create(new CreateAccountRequest("hilla", "hilla@observato.com")))
        .containsRightInstanceOf(Account.class);
  }

  @Test
  void duplicate() {
    repository.create(new CreateAccountRequest("gadi", "gadi@observato.com"));
    VavrAssertions.assertThat(
            repository.create(new CreateAccountRequest("gadi", "gadi@observato.com")))
        .isLeft()
        .containsLeftInstanceOf(SqlError.class);
    //        .containsOnLeft(new SqlError("blah"));
  }
}
