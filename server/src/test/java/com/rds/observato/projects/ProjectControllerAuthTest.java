package com.rds.observato.projects;

import static org.assertj.core.api.Assertions.*;

import com.rds.observato.DatabaseTestBase;
import com.rds.observato.Fixtures;
import com.rds.observato.auth.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

class ProjectControllerAuthTest extends DatabaseTestBase {

  private final ProjectController controller = new ProjectController(repository);
  long user = Fixtures.createUser(repository);
  long account = Fixtures.createAccount(repository, user);
  long project = Fixtures.createProject(repository, account);

  @ParameterizedTest
  @EnumSource(
      value = Role.class,
      names = {"ADMIN"})
  void authorized(Role role) {
    assertThatCode(() -> controller.get(Fixtures.user(role, user, account), project))
        .doesNotThrowAnyException();
  }

  @ParameterizedTest
  @EnumSource(
      value = Role.class,
      names = {"RESOURCE", "SCHEDULER", "ANONYMOUS"})
  void unauthorized(Role role) {
    assertThatThrownBy(() -> controller.get(Fixtures.user(role, user, account), project))
        .isInstanceOf(AuthorisedException.class);
  }
}
