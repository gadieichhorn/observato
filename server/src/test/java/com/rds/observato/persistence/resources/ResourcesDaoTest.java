package com.rds.observato.persistence.resources;

import com.rds.observato.DatabaseTestBase;
import com.rds.observato.api.persistence.Repository;
import com.rds.observato.persistence.RepositoryDao;
import java.sql.SQLException;
import java.util.Set;
import java.util.UUID;
import org.assertj.core.api.Assertions;
import org.jdbi.v3.core.statement.UnableToExecuteStatementException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ResourcesDaoTest extends DatabaseTestBase {

  private Repository repository;
  private long account;

  @BeforeEach
  void setUp() throws SQLException {
    repository = RepositoryDao.create(jdbi());
    long user =
        repository
            .users()
            .create(UUID.randomUUID().toString(), "salt".getBytes(), "hash".getBytes());
    account = repository.accounts().create(UUID.randomUUID().toString(), user);
  }

  @Test
  void create() {
    Assertions.assertThatCode(() -> repository.resources().create(account, "r001"))
        .doesNotThrowAnyException();
  }

  @Test
  void duplicate() {
    repository.resources().create(account, "r002");
    Assertions.assertThatThrownBy(() -> repository.resources().create(account, "r002"))
        .isInstanceOf(UnableToExecuteStatementException.class);
  }

  @Test
  void getAll() {
    long resource = repository.resources().create(account, "r003");
    Set<ResourceView> all = repository.resources().getAll(account);
    Assertions.assertThat(all).contains(new ResourceView(resource, account, "r003"));
  }
}
