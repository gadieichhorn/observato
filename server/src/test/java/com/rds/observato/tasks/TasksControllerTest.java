package com.rds.observato.tasks;

import com.rds.observato.DatabaseTestBase;
import com.rds.observato.Fixtures;
import com.rds.observato.api.persistence.Repository;
import com.rds.observato.api.request.CreateTaskRequest;
import com.rds.observato.api.response.CreateTaskResponse;
import com.rds.observato.auth.ObservatoAuthFilter;
import com.rds.observato.auth.ObservatoBasicAuthenticator;
import com.rds.observato.auth.Role;
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
import org.testcontainers.shaded.com.google.common.collect.ImmutableSet;

@ExtendWith(DropwizardExtensionsSupport.class)
class TasksControllerTest extends DatabaseTestBase {

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
          .addProvider(() -> new TasksController(repository))
          .build();

  long user;
  String token;

  @BeforeEach
  void setUp() {
    user =
        repository
            .users()
            .create(UUID.randomUUID().toString(), "salt".getBytes(), "hash".getBytes());
    token = Fixtures.token(user);
  }

  @Test
  void create() {
    long account = repository.accounts().create(UUID.randomUUID().toString(), user);
    repository.accounts().createUserTokenForAccount(user, account, token);
    repository.accounts().assignUserToAccount(user, account, Role.ADMIN);

    CreateTaskResponse response =
        EXT.target("/tasks/%d".formatted(account))
            .request()
            .header(HttpHeaders.AUTHORIZATION, token)
            .post(Entity.json(new CreateTaskRequest("tsk0002", "description")))
            .readEntity(CreateTaskResponse.class);

    Assertions.assertThat(repository.tasks().finById(account, response.id()))
        .isPresent()
        .get()
        .isInstanceOf(TaskView.class)
        .hasFieldOrPropertyWithValue("id", response.id());
  }

  @Test
  void get() {
    long account = repository.accounts().create(UUID.randomUUID().toString(), user);
    long task = repository.tasks().create(account, "tsk0001", "description");
    repository.accounts().createUserTokenForAccount(user, account, token);
    repository.accounts().assignUserToAccount(user, account, Role.ADMIN);

    Assertions.assertThat(
            EXT.target("/tasks/%d".formatted(account))
                .request()
                .header(HttpHeaders.AUTHORIZATION, token)
                .get(GetTasksResponse.class))
        .hasFieldOrPropertyWithValue(
            "tasks",
            ImmutableSet.of(new GetTaskResponse(task, 0, account, "tsk0001", "description")));
  }
}
