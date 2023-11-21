package com.rds.observato.assignments;

import com.rds.observato.DatabaseTestBase;
import com.rds.observato.Fixtures;
import com.rds.observato.api.persistence.Repository;
import com.rds.observato.auth.AuthorisedException;
import java.time.Instant;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class AssignmentControllerAuthTest extends DatabaseTestBase {

  private static final Repository repository = repository();
  private static final AssignmentController controller = new AssignmentController(repository);

  @Test
  void authorized() {
    long user = Fixtures.createUser(repository);
    long account = Fixtures.createAccount(repository, user);
    long task = Fixtures.createTask(repository, account);
    long resource = Fixtures.createResource(repository, account);
    long assignment =
        repository.assignments().create(account, task, resource, Instant.now(), Instant.now());

    Assertions.assertThatCode(() -> controller.get(Fixtures.admin(), account, assignment))
        .doesNotThrowAnyException();
  }

  @Test
  void unauthorized() {
    long user = Fixtures.createUser(repository);
    long account = Fixtures.createAccount(repository, user);
    long task = Fixtures.createTask(repository, account);
    long resource = Fixtures.createResource(repository, account);
    long assignment =
        repository.assignments().create(account, task, resource, Instant.now(), Instant.now());

    Assertions.assertThatThrownBy(() -> controller.get(Fixtures.scheduler(), account, assignment))
        .isInstanceOf(AuthorisedException.class);
    Assertions.assertThatThrownBy(() -> controller.get(Fixtures.resource(), account, assignment))
        .isInstanceOf(AuthorisedException.class);
    Assertions.assertThatThrownBy(() -> controller.get(Fixtures.anonymous(), account, assignment))
        .isInstanceOf(AuthorisedException.class);
  }
}
