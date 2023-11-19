package com.rds.observato.tasks;

import static org.junit.jupiter.api.Assertions.*;

import com.rds.observato.DatabaseTestBase;
import com.rds.observato.Fixtures;
import com.rds.observato.api.persistence.Repository;
import org.assertj.core.api.Assertions;
import org.jdbi.v3.core.statement.UnableToExecuteStatementException;
import org.junit.jupiter.api.Test;

class TasksDaoTest extends DatabaseTestBase {

  private static final Repository repository = repository();
  private static final long user = Fixtures.createUser(repository);

  @Test
  void create() {
    long account = Fixtures.createAccount(repository, user);
    Assertions.assertThatCode(() -> repository.tasks().create(account, "t001", "tasks"))
        .doesNotThrowAnyException();
  }

  @Test
  void duplicate() {
    long account = Fixtures.createAccount(repository, user);
    repository.tasks().create(account, "t001", "tasks");
    Assertions.assertThatThrownBy(() -> repository.tasks().create(account, "t001", "tasks"))
        .isInstanceOf(UnableToExecuteStatementException.class);
  }

  @Test
  void sameNameDifferentAccount() {
    long account1 = Fixtures.createAccount(repository, user);
    Assertions.assertThatCode(() -> repository.tasks().create(account1, "t001", "tasks"))
        .doesNotThrowAnyException();

    long account2 = Fixtures.createAccount(repository, user);
    Assertions.assertThatCode(() -> repository.tasks().create(account2, "t001", "tasks"))
        .doesNotThrowAnyException();
  }

  @Test
  void findAll() {
    long account = Fixtures.createAccount(repository, user);
    long task = repository.tasks().create(account, "t001", "tasks");
    Assertions.assertThat(repository.tasks().findAll(account))
        .containsExactly(new TaskView(task, 0, account, "t001", "tasks"));
  }

  @Test
  void findAllByProject() {
    long account = Fixtures.createAccount(repository, user);
    long project = Fixtures.createProject(repository, account);
    long task = repository.tasks().create(account, "t001", "tasks");
    repository.projects().assignTaskToProject(account, task, project);
    Assertions.assertThat(repository.tasks().findAllByProject(account, project))
        .containsExactly(new TaskView(task, 0, account, "t001", "tasks"));
  }
}
