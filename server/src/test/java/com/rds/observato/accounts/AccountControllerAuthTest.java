package com.rds.observato.accounts;

import com.rds.observato.DatabaseTestBase;
import com.rds.observato.Fixtures;
import com.rds.observato.auth.AuthorisedException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class AccountControllerAuthTest extends DatabaseTestBase {

  private final AccountController controller = new AccountController(repository);

  @Test
  void authorized() {
    long user = Fixtures.createUser(repository);
    long account = Fixtures.createAccount(repository, user);

    Assertions.assertThatCode(() -> controller.get(Fixtures.admin(), account))
        .doesNotThrowAnyException();
  }

  @Test
  void unauthorized() {
    long user = Fixtures.createUser(repository);
    long account = Fixtures.createAccount(repository, user);

    Assertions.assertThatThrownBy(() -> controller.get(Fixtures.scheduler(), account))
        .isInstanceOf(AuthorisedException.class);
    Assertions.assertThatThrownBy(() -> controller.get(Fixtures.resource(), account))
        .isInstanceOf(AuthorisedException.class);
    Assertions.assertThatThrownBy(() -> controller.get(Fixtures.anonymous(), account))
        .isInstanceOf(AuthorisedException.class);
  }
}
