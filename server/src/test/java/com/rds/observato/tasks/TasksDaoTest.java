package com.rds.observato.tasks;

import static org.assertj.core.api.Assertions.*;

import com.google.common.collect.ImmutableMap;
import com.rds.observato.DatabaseTestBase;
import com.rds.observato.Fixtures;
import com.rds.observato.Repository;
import java.util.Optional;
import org.jdbi.v3.core.statement.UnableToExecuteStatementException;
import org.junit.jupiter.api.Test;

class TasksDaoTest extends DatabaseTestBase {

  private static final Repository repository = repository();
  private static final long user = Fixtures.createUser(repository);

  @Test
  void create() {
    long account = Fixtures.createAccount(repository, user);
    assertThatCode(() -> repository.tasks().create(account, "t001", "tasks"))
        .doesNotThrowAnyException();
  }

  @Test
  void duplicate() {
    long account = Fixtures.createAccount(repository, user);
    repository.tasks().create(account, "t001", "tasks");
    assertThatThrownBy(() -> repository.tasks().create(account, "t001", "tasks"))
        .isInstanceOf(UnableToExecuteStatementException.class);
  }

  @Test
  void sameNameDifferentAccount() {
    long account1 = Fixtures.createAccount(repository, user);
    assertThatCode(() -> repository.tasks().create(account1, "t001", "tasks"))
        .doesNotThrowAnyException();

    long account2 = Fixtures.createAccount(repository, user);
    assertThatCode(() -> repository.tasks().create(account2, "t001", "tasks"))
        .doesNotThrowAnyException();
  }

  @Test
  void findAll() {
    long account = Fixtures.createAccount(repository, user);
    long task = repository.tasks().create(account, "t001", "tasks");
    assertThat(repository.tasks().findAll(account))
        .containsExactly(new TaskView(task, 0, account, "t001", "tasks", ImmutableMap.of()));
  }

  @Test
  void findAllByProject() {
    long account = Fixtures.createAccount(repository, user);
    long project = Fixtures.createProject(repository, account);
    long task = repository.tasks().create(account, "t001", "tasks");
    repository.projects().assignTaskToProject(account, task, project);
    assertThat(repository.tasks().findAllByProject(account, project))
        .containsExactly(new TaskView(task, 0, account, "t001", "tasks", ImmutableMap.of()));
  }

  @Test
  void update() {
    long account = Fixtures.createAccount(repository, user);
    long task =
        repository
            .tasks()
            .create(account, "update", "update is not locked when revision didnt change");

    TaskView old = repository.tasks().finById(account, task).orElseThrow();

    assertThat(
            repository
                .tasks()
                .update(
                    account,
                    task,
                    old.revision(),
                    "updated",
                    "updated description when not locked"))
        .isEqualTo(1);

    Optional<TaskView> updated = repository.tasks().finById(account, task);
    assertThat(updated)
        .isPresent()
        .get()
        .hasFieldOrPropertyWithValue("name", "updated")
        .hasFieldOrPropertyWithValue("description", "updated description when not locked")
        .hasFieldOrPropertyWithValue("revision", 1);
  }

  @Test
  void locked() {
    long account = Fixtures.createAccount(repository, user);
    long task = repository.tasks().create(account, "locked", "locking is fun");

    TaskView old = repository.tasks().finById(account, task).orElseThrow();

    assertThat(repository.tasks().update(account, task, old.revision() + 1, "t011-1", "tasks-1"))
        .isEqualTo(0);

    Optional<TaskView> updated = repository.tasks().finById(account, task);

    assertThat(updated)
        .isPresent()
        .get()
        .hasFieldOrPropertyWithValue("name", "locked")
        .hasFieldOrPropertyWithValue("description", "locking is fun")
        .hasFieldOrPropertyWithValue("revision", 0);
  }
}
