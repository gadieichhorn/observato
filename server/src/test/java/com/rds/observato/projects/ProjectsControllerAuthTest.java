package com.rds.observato.projects;

import com.rds.observato.DatabaseTestBase;
import com.rds.observato.Fixtures;
import com.rds.observato.auth.*;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

class ProjectsControllerAuthTest extends DatabaseTestBase {

  private final ProjectsController controller = new ProjectsController(repository);
  long user = Fixtures.createUser(repository);
  long account = Fixtures.createAccount(repository, user);

  @ParameterizedTest
  @EnumSource(
      value = Role.class,
      names = {"ADMIN"})
  void authorizedPost(Role role) {
    Assertions.assertThatCode(
            () ->
                controller.post(
                    Fixtures.user(role, user, account),
                    new CreateProjectRequest("prj001", "project")))
        .doesNotThrowAnyException();
  }

  @ParameterizedTest
  @EnumSource(
      value = Role.class,
      names = {"ADMIN"})
  void authorizedGet(Role role) {
    Assertions.assertThatCode(() -> controller.get(Fixtures.user(role, user, account)))
        .doesNotThrowAnyException();
  }

  @ParameterizedTest
  @EnumSource(
      value = Role.class,
      names = {"RESOURCE", "SCHEDULER", "ANONYMOUS"})
  void unauthorizedPost(Role role) {
    Assertions.assertThatThrownBy(
            () ->
                controller.post(
                    Fixtures.user(role, user, account),
                    new CreateProjectRequest("prj001", "project")))
        .isInstanceOf(AuthorisedException.class);
  }

  @ParameterizedTest
  @EnumSource(
      value = Role.class,
      names = {"RESOURCE", "SCHEDULER", "ANONYMOUS"})
  void unauthorizedGet(Role role) {
    Assertions.assertThatThrownBy(() -> controller.get(Fixtures.user(role, user, account)))
        .isInstanceOf(AuthorisedException.class);
  }
}
