package com.rds.observato.accounts;

import com.rds.observato.ControllerBaseTest;
import com.rds.observato.auth.Role;
import io.dropwizard.testing.junit5.ResourceExtension;
import jakarta.ws.rs.core.HttpHeaders;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class AccountControllerTest extends ControllerBaseTest {

  private final ResourceExtension EXT = ext(() -> new AccountController(repository));

  @Test
  void get() {
    long account = repository.accounts().create("acc0001", user);
    repository.accounts().createUserTokenForAccount(user, account, token);
    repository.accounts().assignUserToAccount(user, account, Role.ADMIN);

    Assertions.assertThat(
            EXT.target("/accounts/%d".formatted(account))
                .request()
                .header(HttpHeaders.AUTHORIZATION, token)
                .get(GetAccountResponse.class))
        .isNotNull()
        .isInstanceOf(GetAccountResponse.class)
        .hasFieldOrPropertyWithValue("name", "acc0001")
        .hasFieldOrPropertyWithValue("owner", user);
  }
}
