package com.rds.observato.projects;

import com.rds.observato.DatabaseTestBase;
import com.rds.observato.Fixtures;
import com.rds.observato.api.persistence.Repository;
import com.rds.observato.api.response.GetProjectResponse;
import com.rds.observato.auth.ObservatoAuthFilter;
import com.rds.observato.auth.ObservatoBasicAuthenticator;
import com.rds.observato.auth.Roles;
import com.rds.observato.auth.User;
import io.dropwizard.auth.AuthDynamicFeature;
import io.dropwizard.auth.AuthFilter;
import io.dropwizard.auth.AuthValueFactoryProvider;
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
class ProjectControllerTest extends DatabaseTestBase {

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
          .addProvider(() -> new ProjectController(repository))
          .build();

  long user;
  String token;

  @BeforeEach
  void setUp() {
    user = Fixtures.createUser(repository);
    token = Fixtures.token(user);
  }

  @Test
  void get() {
    long account = repository.accounts().create(UUID.randomUUID().toString(), user);
    long project = repository.projects().create(account, "prj0001", "description");
    repository.accounts().createUserTokenForAccount(user, account, token);
    repository.accounts().assignUserToAccount(user, account, Roles.ADMIN);

    Assertions.assertThat(
            EXT.target("/projects/%d/%d".formatted(account, project))
                .request()
                .header(HttpHeaders.AUTHORIZATION, token)
                .get(GetProjectResponse.class))
        .isNotNull()
        .isInstanceOf(GetProjectResponse.class)
        .hasFieldOrPropertyWithValue("id", project)
        .hasFieldOrPropertyWithValue("name", "prj0001");
  }
}
