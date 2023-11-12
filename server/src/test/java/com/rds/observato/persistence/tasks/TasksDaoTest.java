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
import org.junit.jupiter.api.DisplayName;
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
    tasksDao.create(account, "t006", "projects");
    Assertions.assertThatThrownBy(() -> tasksDao.create(account, "t006", "projects"))
        .isInstanceOf(UnableToExecuteStatementException.class);
  }

  @Test
  void getAll() {
    long id = tasksDao.create(account, "p005", "projects");
    Assertions.assertThat(tasksDao.findAll(account))
        .hasSizeGreaterThan(0)
        .contains(new TaskView(id, account, "p005", "projects"));
  }

  @Test
  void getProjectTasks() {
    final long project = projectsDao.create(account, "p001", "tasks");
    final long task1 = tasksDao.create(account, "t001", "tasks");
    final long task2 = tasksDao.create(account, "t002", "tasks");
    final long task3 = tasksDao.create(account, "t003", "tasks");
    projectsDao.assignTaskToProject(account, task1, project);
    projectsDao.assignTaskToProject(account, task2, project);
    Assertions.assertThat(tasksDao.findAllByProject(account, project))
        .hasSize(2)
        .contains(new TaskView(task1, account, "t001", "tasks"))
        .contains(new TaskView(task2, account, "t002", "tasks"))
        .doesNotContain(new TaskView(task3, account, "t003", "tasks"));
  }

  @Test
  @DisplayName("same task for same projects is not allowed")
  void duplicateRelations() {
    final long project = projectsDao.create(account, "p002", "tasks");
    final long task1 = tasksDao.create(account, "t005", "tasks");
    projectsDao.assignTaskToProject(account, task1, project);
    Assertions.assertThatThrownBy(() -> projectsDao.assignTaskToProject(account, task1, project))
        .isInstanceOf(UnableToExecuteStatementException.class);
  }

  @Test
  @DisplayName("same task for different projects is not allowed")
  void duplicateTaskRelations() {
    final long project1 = projectsDao.create(account, "p003", "tasks");
    final long project2 = projectsDao.create(account, "p004", "tasks");
    final long task1 = tasksDao.create(account, "t006", "tasks");
    projectsDao.assignTaskToProject(account, task1, project1);
    Assertions.assertThatThrownBy(() -> projectsDao.assignTaskToProject(account, task1, project2))
        .isInstanceOf(UnableToExecuteStatementException.class);
  }
}
