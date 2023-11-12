package com.rds.observato.persistence.tasks;

import static org.junit.jupiter.api.Assertions.*;

import com.github.javafaker.Faker;
import com.rds.observato.DatabaseTestBase;
import com.rds.observato.api.persistence.Repository;
import com.rds.observato.persistence.accounts.AccountDao;
import com.rds.observato.persistence.projects.ProjectsDao;
import java.sql.SQLException;
import java.util.UUID;
import org.assertj.core.api.Assertions;
import org.jdbi.v3.core.statement.UnableToExecuteStatementException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TasksDaoTest extends DatabaseTestBase {

  private final Faker faker = new Faker();
  private AccountDao accountDao;
  private TasksDao tasksDao;
  private ProjectsDao projectsDao;

  private long account;

  @BeforeEach
  void setUp() throws SQLException {
    Repository repository = repository();
    accountDao = repository.accounts();
    tasksDao = repository.tasks();
    projectsDao = repository.projects();
    account = accountDao.create(UUID.randomUUID().toString(), "tasks");
  }

  @Test
  void insert() {
    long tsk = tasksDao.create(account, faker.company().name(), faker.company().catchPhrase());
    Assertions.assertThat(tsk).isGreaterThan(0);
  }

  @Test
  void wrongAccount() {
    String name = faker.company().name();
    String phrase = faker.company().catchPhrase();
    Assertions.assertThatThrownBy(() -> tasksDao.create(0l, name, phrase))
        .isInstanceOf(UnableToExecuteStatementException.class);
  }

  @Test
  void duplicate() {
    String name = faker.company().name();
    String phrase = faker.company().catchPhrase();
    tasksDao.create(account, name, phrase);
    Assertions.assertThatThrownBy(() -> tasksDao.create(account, name, phrase))
        .isInstanceOf(UnableToExecuteStatementException.class);
  }

  @Test
  void getAll() {
    long id = tasksDao.create(account, "proj005", "find all");
    Assertions.assertThat(tasksDao.findAll(account))
        .hasSizeGreaterThan(0)
        .contains(new TaskView(id, account, "proj005", "find all"));
  }

  //  @RepeatedTest(10)
  @Test
  void getProjectTasks() {
    final long project = projectsDao.create(account, "proj12123", "find all");
    final long task = tasksDao.create(account, "tsk123123", "tasks");
    projectsDao.assignTaskToProject(account, task, project);
    Assertions.assertThat(tasksDao.findAllByProject(account, project))
        .hasSizeGreaterThan(0)
        .contains(new TaskView(task, account, "tsk123123", "tasks"));
  }
}
