package com.rds.observato.accounts;

import com.rds.observato.ControllerBaseTest;
import com.rds.observato.auth.Role;
import io.dropwizard.testing.junit5.ResourceExtension;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.core.HttpHeaders;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.testcontainers.shaded.com.google.common.collect.ImmutableSet;

class AccountsControllerTest extends ControllerBaseTest {

  private final ResourceExtension EXT = ext(() -> new AccountsController(repository));

  @Disabled("user can only create account is special permissions. owner status when paid or other")
  void post() {
    CreateAccountResponse response =
        EXT.target("/accounts")
            .request()
            .header(HttpHeaders.AUTHORIZATION, token)
            .post(Entity.json(new CreateAccountRequest("acc0003", user)))
            .readEntity(CreateAccountResponse.class);

    Assertions.assertThat(repository.accounts().findById(response.id()))
        .isPresent()
        .get()
        .isInstanceOf(AccountRecord.class)
        .hasFieldOrPropertyWithValue("id", response.id())
        .hasFieldOrPropertyWithValue("revision", 0)
        .hasFieldOrPropertyWithValue("name", "acc0003");
  }

  //  @Test
  void get() {
    long account = repository.accounts().create("acc0005", user);
    repository.accounts().createUserTokenForAccount(user, account, "secret");
    repository.accounts().assignUserToAccount(user, account, Role.ADMIN);

    Assertions.assertThat(
            EXT.target("/accounts")
                .request()
                .header(HttpHeaders.AUTHORIZATION, token)
                .get(GetAccountsResponse.class))
        .isNotNull()
        .isInstanceOf(GetAccountsResponse.class)
        .hasFieldOrPropertyWithValue(
            "accounts", ImmutableSet.of(new GetAccountResponse(account, 0, "acc0005", user)));
  }
}
