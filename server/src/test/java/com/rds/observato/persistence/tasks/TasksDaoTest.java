package com.rds.observato.persistence.tasks;

import static org.junit.jupiter.api.Assertions.*;

import com.rds.observato.DatabaseTestBase;
import com.rds.observato.api.persistence.Repository;
import java.sql.SQLException;
import java.util.UUID;
import org.assertj.core.api.Assertions;
import org.jdbi.v3.core.statement.UnableToExecuteStatementException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class TasksDaoTest extends DatabaseTestBase {

  private long account;
  private Repository repository;

  @BeforeEach
  void setUp() throws SQLException {
    repository = repository();
    long user =
        repository
            .users()
            .create(UUID.randomUUID().toString(), "salt".getBytes(), "hash".getBytes());
    account = repository.accounts().create(UUID.randomUUID().toString(), user);
  }

  @Test
  void insert() {
    long tsk = repository.tasks().create(account, "t030", "tasks");
    Assertions.assertThat(tsk).isGreaterThan(0);
  }

  @Test
  void wrongAccount() {
    Assertions.assertThatThrownBy(() -> repository.tasks().create(0l, "t003", "tasks"))
        .isInstanceOf(UnableToExecuteStatementException.class);
  }

  @Test
  void duplicate() {
    repository.tasks().create(account, "t006", "projects");
    Assertions.assertThatThrownBy(() -> repository.tasks().create(account, "t006", "projects"))
        .isInstanceOf(UnableToExecuteStatementException.class);
  }

  @Test
  void getAll() {
    long id = repository.tasks().create(account, "p005", "projects");
    Assertions.assertThat(repository.tasks().findAll(account))
        .hasSizeGreaterThan(0)
        .contains(new TaskView(id, account, "p005", "projects"));
  }

  @Test
  void getProjectTasks() {
    final long project = repository.projects().create(account, "p001", "tasks");
    final long task1 = repository.tasks().create(account, "t001", "tasks");
    final long task2 = repository.tasks().create(account, "t002", "tasks");
    final long task3 = repository.tasks().create(account, "t003", "tasks");
    repository.projects().assignTaskToProject(account, task1, project);
    repository.projects().assignTaskToProject(account, task2, project);
    Assertions.assertThat(repository.tasks().findAllByProject(account, project))
        .hasSize(2)
        .contains(new TaskView(task1, account, "t001", "tasks"))
        .contains(new TaskView(task2, account, "t002", "tasks"))
        .doesNotContain(new TaskView(task3, account, "t003", "tasks"));
  }

  @Test
  @DisplayName("same task for same projects is not allowed")
  void duplicateRelations() {
    final long project = repository.projects().create(account, "p002", "tasks");
    final long task1 = repository.tasks().create(account, "t005", "tasks");
    repository.projects().assignTaskToProject(account, task1, project);
    Assertions.assertThatThrownBy(
            () -> repository.projects().assignTaskToProject(account, task1, project))
        .isInstanceOf(UnableToExecuteStatementException.class);
  }

  @Test
  @DisplayName("same task for different projects is not allowed")
  void duplicateTaskRelations() {
    final long project1 = repository.projects().create(account, "p003", "tasks");
    final long project2 = repository.projects().create(account, "p004", "tasks");
    final long task1 = repository.tasks().create(account, "t006", "tasks");
    repository.projects().assignTaskToProject(account, task1, project1);
    Assertions.assertThatThrownBy(
            () -> repository.projects().assignTaskToProject(account, task1, project2))
        .isInstanceOf(UnableToExecuteStatementException.class);
  }
}
