package com.rds.observato.persistence.projects;

import static org.junit.jupiter.api.Assertions.*;

import com.rds.observato.DatabaseTestBase;
import com.rds.observato.api.persistence.Repository;
import com.rds.observato.persistence.accounts.AccountDao;
import java.sql.SQLException;
import java.util.UUID;
import org.assertj.core.api.Assertions;
import org.jdbi.v3.core.statement.UnableToExecuteStatementException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ProjectsDaoTest extends DatabaseTestBase {

  private AccountDao accountDao;
  private ProjectsDao projectsDao;

  private long account;

  @BeforeEach
  void setUp() throws SQLException {
    Repository repository = repository();
    accountDao = repository.accounts();
    projectsDao = repository.projects();
    account = accountDao.create(UUID.randomUUID().toString(), "me");
  }

  @Test
  void insert() {
    long proj = projectsDao.create(account, "proj001", "some project");
    Assertions.assertThat(proj).isGreaterThan(0);
  }

  @Test
  void findByName() {
    long proj = projectsDao.create(account, "proj004", "some project");
    Assertions.assertThat(projectsDao.findByName(account, "proj004"))
        .isPresent()
        .get()
        .isInstanceOf(ProjectView.class)
        .hasFieldOrPropertyWithValue("name", "proj004")
        .hasFieldOrPropertyWithValue("description", "some project");
  }

  @Test
  void wrongAccount() {
    Assertions.assertThatThrownBy(() -> projectsDao.create(0l, "proj003", "some project"))
        .isInstanceOf(UnableToExecuteStatementException.class);
  }

  @Test
  void duplicate() {
    projectsDao.create(account, "proj002", "some project");
    Assertions.assertThatThrownBy(() -> projectsDao.create(account, "proj002", "some project"))
        .isInstanceOf(UnableToExecuteStatementException.class);
  }

  @Test
  void getAll() {
    long id = projectsDao.create(account, "proj005", "find all");
    Assertions.assertThat(projectsDao.findAll(account))
        .hasSizeGreaterThan(0)
        .contains(new ProjectView(id, account, "proj005", "find all"));
  }
}
