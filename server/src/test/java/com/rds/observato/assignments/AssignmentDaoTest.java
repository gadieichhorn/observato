package com.rds.observato.assignments;

import com.rds.observato.DatabaseTestBase;
import com.rds.observato.Fixtures;
import java.time.Instant;
import org.assertj.core.api.Assertions;
import org.jdbi.v3.core.statement.UnableToExecuteStatementException;
import org.junit.jupiter.api.Test;

class AssignmentDaoTest extends DatabaseTestBase {

  private final long user = Fixtures.createUser(repository);

  @Test
  void create() {
    long account = Fixtures.createAccount(repository, user);
    long task = Fixtures.createTask(repository, account);
    long resource = Fixtures.createResource(repository, account);

    Assertions.assertThatCode(
            () ->
                repository
                    .assignments()
                    .create(account, task, resource, Instant.now(), Instant.now()))
        .doesNotThrowAnyException();
  }

  @Test
  void duplicate() {
    long account = Fixtures.createAccount(repository, user);
    long task = Fixtures.createTask(repository, account);
    long resource = Fixtures.createResource(repository, account);

    repository.assignments().create(account, task, resource, Instant.now(), Instant.now());
    Assertions.assertThatThrownBy(
            () ->
                repository
                    .assignments()
                    .create(account, task, resource, Instant.now(), Instant.now()))
        .isInstanceOf(UnableToExecuteStatementException.class);
  }

  @Test
  void findAll() {
    long account = Fixtures.createAccount(repository, user);
    long task = Fixtures.createTask(repository, account);
    long resource = Fixtures.createResource(repository, account);
    repository.assignments().create(account, task, resource, Instant.now(), Instant.now());
    Assertions.assertThat(repository.assignments().findAll(account)).isNotEmpty();
  }

  @Test
  void findById() {
    long account = Fixtures.createAccount(repository, user);
    long task = Fixtures.createTask(repository, account);
    long resource = Fixtures.createResource(repository, account);
    long assignment =
        repository.assignments().create(account, task, resource, Instant.now(), Instant.now());
    Assertions.assertThat(repository.assignments().findById(account, assignment)).isNotEmpty();
  }
}
