package com.rds.observato.projects;

import com.rds.observato.DatabaseTestBase;
import com.rds.observato.Fixtures;
import com.rds.observato.api.persistence.Repository;
import com.rds.observato.persistence.RepositoryDao;
import org.assertj.core.api.Assertions;
import org.jdbi.v3.core.statement.UnableToExecuteStatementException;
import org.junit.jupiter.api.Test;

class ProjectsDaoTest extends DatabaseTestBase {

  private static final Repository repository = RepositoryDao.create(jdbi());

  private static final long user = Fixtures.createUser(repository);

  @Test
  void insert() {
    long account = Fixtures.createAccount(repository, user);
    long proj = repository.projects().create(account, "proj001", "some project");
    Assertions.assertThat(proj).isGreaterThan(0);
  }

  @Test
  void findByName() {
    long account = Fixtures.createAccount(repository, user);
    long project = repository.projects().create(account, "proj004", "some project");
    Assertions.assertThat(repository.projects().findById(account, project))
        .isPresent()
        .get()
        .isInstanceOf(ProjectView.class)
        .hasFieldOrPropertyWithValue("name", "proj004")
        .hasFieldOrPropertyWithValue("description", "some project");
  }

  @Test
  void wrongAccount() {
    Assertions.assertThatThrownBy(() -> repository.projects().create(0l, "proj003", "some project"))
        .isInstanceOf(UnableToExecuteStatementException.class);
  }

  @Test
  void duplicate() {
    long account = Fixtures.createAccount(repository, user);
    repository.projects().create(account, "proj002", "some project");
    Assertions.assertThatThrownBy(
            () -> repository.projects().create(account, "proj002", "some project"))
        .isInstanceOf(UnableToExecuteStatementException.class);
  }

  @Test
  void getAll() {
    long account = Fixtures.createAccount(repository, user);
    long id = repository.projects().create(account, "proj005", "find all");
    Assertions.assertThat(repository.projects().findAll(account))
        .hasSizeGreaterThan(0)
        .contains(new ProjectView(id, account, "proj005", "find all"));
  }
}