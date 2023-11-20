package com.rds.observato.accounts;

import com.rds.observato.DatabaseTestBase;
import com.rds.observato.api.persistence.Repository;
import io.dropwizard.testing.junit5.DropwizardExtensionsSupport;
import io.dropwizard.testing.junit5.ResourceExtension;
import jakarta.ws.rs.core.HttpHeaders;
import java.util.UUID;
import org.assertj.core.api.Assertions;
import org.glassfish.jersey.test.grizzly.GrizzlyWebTestContainerFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(DropwizardExtensionsSupport.class)
class AccountControllerTest extends DatabaseTestBase {

  private static Repository repository = repository();
  public static final ResourceExtension EXT =
      ResourceExtension.builder()
          .setTestContainerFactory(new GrizzlyWebTestContainerFactory())
          .addProvider(() -> new AccountController(repository))
          .build();

  static long user;

  @BeforeEach
  void setUp() {
    user =
        repository
            .users()
            .create(UUID.randomUUID().toString(), "salt".getBytes(), "hash".getBytes());
  }

  @Test
  void get() {
    long account = repository.accounts().create("acc0001", user);

    Assertions.assertThat(
            EXT.target("/accounts/%d".formatted(account))
                .request()
                .header(HttpHeaders.AUTHORIZATION, "secret")
                .get(GetAccountResponse.class))
        .isNotNull()
        .isInstanceOf(GetAccountResponse.class)
        .hasFieldOrPropertyWithValue("name", "acc0001")
        .hasFieldOrPropertyWithValue("owner", user);
  }
}
