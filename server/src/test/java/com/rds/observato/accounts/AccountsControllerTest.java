package com.rds.observato.accounts;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import com.rds.observato.DatabaseTestBase;
import com.rds.observato.Fixtures;
import com.rds.observato.api.persistence.Repository;
import com.rds.observato.api.request.CreateAccountRequest;
import com.rds.observato.api.response.CreateAccountResponse;
import com.rds.observato.api.response.GetAccountsResponse;
import com.rds.observato.auth.ObservatoAuthFilter;
import com.rds.observato.auth.ObservatoBasicAuthenticator;
import com.rds.observato.auth.Roles;
import com.rds.observato.auth.User;
import io.dropwizard.auth.AuthDynamicFeature;
import io.dropwizard.auth.AuthFilter;
import io.dropwizard.auth.AuthValueFactoryProvider;
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

  private static final AuthFilter<String, User> BASIC_AUTH_HANDLER =
      new ObservatoAuthFilter.Builder()
          .setAuthenticator(new ObservatoBasicAuthenticator(repository))
          .setRealm("OBSERVATO")
          .buildAuthFilter();
  public static final ResourceExtension EXT =
      ResourceExtension.builder()
          .setTestContainerFactory(new GrizzlyWebTestContainerFactory())
          .addProvider(new AuthDynamicFeature(BASIC_AUTH_HANDLER))
          .addProvider(new AuthValueFactoryProvider.Binder<>(User.class))
          .addProvider(() -> new AccountsController(repository))
          .build();

  long user;
  String token;

  @BeforeEach
  void setUp() {
    user = Fixtures.createUser(repository);
    token = Fixtures.token(user);
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
    repository.accounts().createUserTokenForAccount(user, account, "secret");
    repository.accounts().assignUserToAccount(user, account, Roles.ADMIN);

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
