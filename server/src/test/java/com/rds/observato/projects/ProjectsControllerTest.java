package com.rds.observato.projects;

import com.google.common.collect.ImmutableSet;
import com.rds.observato.DatabaseTestBase;
import com.rds.observato.api.persistence.Repository;
import com.rds.observato.api.response.GetProjectResponse;
import com.rds.observato.api.response.GetProjectsResponse;
import com.rds.observato.auth.ObservatoAuthFilter;
import com.rds.observato.auth.ObservatoBasicAuthenticator;
import com.rds.observato.auth.User;
import io.dropwizard.auth.AuthDynamicFeature;
import io.dropwizard.auth.AuthFilter;
import io.dropwizard.auth.AuthValueFactoryProvider;
import io.dropwizard.testing.junit5.DropwizardExtensionsSupport;
import io.dropwizard.testing.junit5.ResourceExtension;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.core.HttpHeaders;
import java.util.UUID;
import org.assertj.core.api.Assertions;
import org.glassfish.jersey.test.grizzly.GrizzlyWebTestContainerFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(DropwizardExtensionsSupport.class)
class ProjectsControllerTest extends DatabaseTestBase {

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
          .addProvider(() -> new ProjectsController(repository))
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
    long account = repository.accounts().create(UUID.randomUUID().toString(), user);
    long project = repository.projects().create(account, "prj0002", "description");
    Assertions.assertThat(
            EXT.target("/projects/%d".formatted(account))
                .request()
                .header(HttpHeaders.AUTHORIZATION, "secret")
                .get(GetProjectsResponse.class))
        .isNotNull()
        .isInstanceOf(GetProjectsResponse.class)
        .hasFieldOrPropertyWithValue(
            "projects",
            ImmutableSet.of(new GetProjectResponse(project, account, "prj0002", "description")));
  }

  @Test
  void post() {
    long account = repository.accounts().create(UUID.randomUUID().toString(), user);
    CreateProjectResponse response =
        EXT.target("/projects/%d".formatted(account))
            .request()
            .header(HttpHeaders.AUTHORIZATION, "secret")
            .post(Entity.json(new CreateProjectRequest("prj00003", "description")))
            .readEntity(CreateProjectResponse.class);

    Assertions.assertThat(repository.projects().findById(account, response.id()))
        .isPresent()
        .get()
        .isInstanceOf(ProjectView.class)
        .hasFieldOrPropertyWithValue("name", "prj00003")
        .hasFieldOrPropertyWithValue("description", "description");
  }
}
