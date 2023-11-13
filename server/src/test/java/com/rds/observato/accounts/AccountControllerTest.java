package com.rds.observato.accounts;

import static org.assertj.core.api.Assertions.assertThat;

import com.rds.observato.DatabaseTestBase;
import com.rds.observato.Fixtures;
import com.rds.observato.api.persistence.Repository;
import org.junit.jupiter.api.Test;

class AccountControllerTest extends DatabaseTestBase {

  private static final Repository repository = repository();
  private static final long user = Fixtures.createUser(repository);

  @Test
  void create() {
    long account = Fixtures.createAccount(repository, user);
    AccountController controller = new AccountController(repository);
    assertThat(controller.getOne(account)).isPresent().get().isInstanceOf(GetAccountResponse.class);
  }
}
