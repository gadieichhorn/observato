package com.rds.observato.assignments;

import static org.assertj.core.api.Assertions.*;

import com.rds.observato.DatabaseTestBase;
import com.rds.observato.Fixtures;
import com.rds.observato.auth.AuthorisedException;
import org.junit.jupiter.api.Test;

class AssignmentsControllerAuthTest extends DatabaseTestBase {

  private final AssignmentsController controller = new AssignmentsController(repository);

  @Test
  void authorized() {
    assertThatCode(() -> controller.get(Fixtures.admin())).doesNotThrowAnyException();
  }

  @Test
  void unauthorized() {
    assertThatThrownBy(() -> controller.get(Fixtures.scheduler()))
        .isInstanceOf(AuthorisedException.class);
    assertThatThrownBy(() -> controller.get(Fixtures.resource()))
        .isInstanceOf(AuthorisedException.class);
    assertThatThrownBy(() -> controller.get(Fixtures.anonymous()))
        .isInstanceOf(AuthorisedException.class);
  }
}
