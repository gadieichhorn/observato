package com.rds.observato.accounts;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import com.rds.observato.DatabaseTestBase;
import com.rds.observato.Fixtures;
import com.rds.observato.api.persistence.Repository;
import com.rds.observato.api.request.CreateAccountRequest;
import com.rds.observato.api.response.CreateAccountResponse;
import com.rds.observato.api.response.GetAccountsResponse;
import io.dropwizard.testing.junit5.DropwizardExtensionsSupport;
import io.dropwizard.testing.junit5.ResourceExtension;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.core.HttpHeaders;
import org.assertj.core.api.Assertions;
import org.glassfish.jersey.test.grizzly.GrizzlyWebTestContainerFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testcontainers.shaded.com.google.common.collect.ImmutableSet;

@ExtendWith(DropwizardExtensionsSupport.class)
class AccountsControllerTest extends DatabaseTestBase {

  private static Repository repository = repository();
  public static final ResourceExtension EXT =
      ResourceExtension.builder()
          .setTestContainerFactory(new GrizzlyWebTestContainerFactory())
          .addProvider(() -> new AccountsController(repository))
          .build();

  static long user;

  @BeforeEach
  void setUp() {
    user = Fixtures.createUser(repository);
  }

  @Test
  void post() {
    CreateAccountResponse response =
        EXT.target("/accounts")
            .request()
            .header(HttpHeaders.AUTHORIZATION, "secret")
            .post(Entity.json(new CreateAccountRequest("acc0003", user)))
            .readEntity(CreateAccountResponse.class);

    Assertions.assertThat(repository.accounts().findById(response.id()))
        .isPresent()
        .get()
        .isInstanceOf(AccountView.class)
        .hasFieldOrPropertyWithValue("id", response.id())
        .hasFieldOrPropertyWithValue("revision", 0)
        .hasFieldOrPropertyWithValue("name", "acc0003");
  }

  //  @Test
  void get() {
    long account = repository.accounts().create("acc0005", user);

    Assertions.assertThat(
            EXT.target("/accounts")
                .request()
                .header(HttpHeaders.AUTHORIZATION, "secret")
                .get(GetAccountsResponse.class))
        .isNotNull()
        .isInstanceOf(GetAccountsResponse.class)
        .hasFieldOrPropertyWithValue(
            "accounts", ImmutableSet.of(new GetAccountResponse(account, 0, "acc0005", user)));
  }
}
