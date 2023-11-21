package com.rds.observato.projects;

import com.rds.observato.DatabaseTestBase;
import com.rds.observato.Fixtures;
import com.rds.observato.api.persistence.Repository;
import com.rds.observato.auth.*;
import java.util.stream.Stream;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.provider.Arguments;

class ProjectControllerAuthTest extends DatabaseTestBase {

  private static final Repository repository = repository();
  private final ProjectController controller = new ProjectController(repository);

  static Stream<Arguments> authorized = Stream.of(Arguments.of(Fixtures.admin()));

  @Test
  void authorized() {
    long user = Fixtures.createUser(repository);
    long account = Fixtures.createAccount(repository, user);
    long project = Fixtures.createProject(repository, account);
    Assertions.assertThatCode(() -> controller.get(Fixtures.admin(), account, project))
        .doesNotThrowAnyException();
  }

  @Test
  void unauthorized() {
    long user = Fixtures.createUser(repository);
    long account = Fixtures.createAccount(repository, user);
    long project = Fixtures.createProject(repository, account);

    Assertions.assertThatThrownBy(() -> controller.get(Fixtures.scheduler(), account, project))
        .isInstanceOf(AuthorisedException.class);
    Assertions.assertThatThrownBy(() -> controller.get(Fixtures.resource(), account, project))
        .isInstanceOf(AuthorisedException.class);
    Assertions.assertThatThrownBy(() -> controller.get(Fixtures.anonymous(), account, project))
        .isInstanceOf(AuthorisedException.class);
  }
}
