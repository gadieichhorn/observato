package com.rds.observato.persistence.projects;

import static org.junit.jupiter.api.Assertions.*;

import com.rds.observato.DatabaseTestBase;
import com.rds.observato.api.persistence.Repository;
import com.rds.observato.persistence.RepositoryDao;
import java.sql.SQLException;
import java.util.UUID;
import org.assertj.core.api.Assertions;
import org.jdbi.v3.core.statement.UnableToExecuteStatementException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ProjectsDaoTest extends DatabaseTestBase {

  private long account;
  Repository repository;

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
  void insert() {
    long proj = repository.projects().create(account, "proj001", "some project");
    Assertions.assertThat(proj).isGreaterThan(0);
  }

  @Test
  void findByName() {
    repository.projects().create(account, "proj004", "some project");
    Assertions.assertThat(repository.projects().findByName(account, "proj004"))
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
    repository.projects().create(account, "proj002", "some project");
    Assertions.assertThatThrownBy(
            () -> repository.projects().create(account, "proj002", "some project"))
        .isInstanceOf(UnableToExecuteStatementException.class);
  }

  @Test
  void getAll() {
    long id = repository.projects().create(account, "proj005", "find all");
    Assertions.assertThat(repository.projects().findAll(account))
        .hasSizeGreaterThan(0)
        .contains(new ProjectView(id, account, "proj005", "find all"));
  }
}
