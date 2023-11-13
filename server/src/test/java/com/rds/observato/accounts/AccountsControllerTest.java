package com.rds.observato.accounts;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import com.rds.observato.DatabaseTestBase;
import com.rds.observato.Fixtures;
import com.rds.observato.api.persistence.Repository;
import com.rds.observato.api.request.CreateAccountRequest;
import com.rds.observato.api.response.CreateAccountResponse;
import org.junit.jupiter.api.Test;

class AccountsControllerTest extends DatabaseTestBase {

  private static final Repository repository = repository();
  private static final long user = Fixtures.createUser(repository);

  @Test
  void create() {
    AccountsController controller = new AccountsController(repository);
    assertThat(controller.create(new CreateAccountRequest("a001", user)))
        .isNotNull()
        .isInstanceOf(CreateAccountResponse.class);
  }
}
