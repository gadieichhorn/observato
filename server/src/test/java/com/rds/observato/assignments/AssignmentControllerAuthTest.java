package com.rds.observato.assignments;

import com.rds.observato.DatabaseTestBase;
import com.rds.observato.Fixtures;
import com.rds.observato.auth.AuthorisedException;
import com.rds.observato.auth.Role;
import java.time.Instant;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class AssignmentControllerAuthTest extends DatabaseTestBase {

  private final AssignmentController controller = new AssignmentController(repository);
  long user = Fixtures.createUser(repository);

  @Test
  void authorized() {
    long account = Fixtures.createAccount(repository, user);
    long task = Fixtures.createTask(repository, account);
    long resource = Fixtures.createResource(repository, account);
    long assignment =
        repository.assignments().create(account, task, resource, Instant.now(), Instant.now());

    Assertions.assertThatCode(
            () -> controller.get(Fixtures.user(Role.ADMIN, user, account), assignment))
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

    Assertions.assertThatThrownBy(
            () -> controller.get(Fixtures.user(Role.SCHEDULER, user, account), assignment))
        .isInstanceOf(AuthorisedException.class);
    Assertions.assertThatThrownBy(
            () -> controller.get(Fixtures.user(Role.RESOURCE, user, account), assignment))
        .isInstanceOf(AuthorisedException.class);
    Assertions.assertThatThrownBy(
            () -> controller.get(Fixtures.user(Role.ANONYMOUS, user, account), assignment))
        .isInstanceOf(AuthorisedException.class);
  }
}
