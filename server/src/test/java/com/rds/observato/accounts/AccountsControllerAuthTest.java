package com.rds.observato.accounts;

import com.rds.observato.DatabaseTestBase;
import com.rds.observato.Fixtures;
import com.rds.observato.api.request.CreateAccountRequest;
import com.rds.observato.auth.AuthorisedException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class AccountsControllerAuthTest extends DatabaseTestBase {

  private final AccountsController controller = new AccountsController(repository);

  @Test
  void authorized() {
    long user = Fixtures.createUser(repository);
    Assertions.assertThatCode(
            () -> controller.post(Fixtures.admin(), new CreateAccountRequest("acc0004", user)))
        .doesNotThrowAnyException();
  }

  @Test
  void unauthorized() {
    long user = Fixtures.createUser(repository);
    Assertions.assertThatThrownBy(
            () -> controller.post(Fixtures.scheduler(), new CreateAccountRequest("acc0004", user)))
        .isInstanceOf(AuthorisedException.class);
    Assertions.assertThatThrownBy(
            () -> controller.post(Fixtures.resource(), new CreateAccountRequest("acc0004", user)))
        .isInstanceOf(AuthorisedException.class);
    Assertions.assertThatThrownBy(
            () -> controller.post(Fixtures.anonymous(), new CreateAccountRequest("acc0004", user)))
        .isInstanceOf(AuthorisedException.class);
  }
}
