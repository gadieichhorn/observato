package com.rds.observato.assignments;

import static org.junit.jupiter.api.Assertions.*;

import com.rds.observato.DatabaseTestBase;
import com.rds.observato.Fixtures;
import com.rds.observato.api.persistence.Repository;
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
import java.time.Instant;
import java.util.UUID;
import org.assertj.core.api.Assertions;
import org.glassfish.jersey.test.grizzly.GrizzlyWebTestContainerFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testcontainers.shaded.com.google.common.collect.ImmutableSet;

@ExtendWith(DropwizardExtensionsSupport.class)
class AssignmentsControllerTest extends DatabaseTestBase {

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
          .addProvider(() -> new AssignmentsController(repository))
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
  void get() {
    long account = repository.accounts().create(UUID.randomUUID().toString(), user);
    repository.accounts().createUserTokenForAccount(user, account, token);
    long task = repository.tasks().create(account, "tsk0003", "description");
    long resource = repository.resources().create(account, "rsc0003");
    long assignment =
        repository
            .assignments()
            .create(
                account,
                task,
                resource,
                Instant.parse("2023-01-01T00:00:00Z"),
                Instant.parse("2023-01-01T01:00:00Z"));

    Assertions.assertThat(
            EXT.target("/assignments/%d".formatted(account))
                .request()
                .header(HttpHeaders.AUTHORIZATION, token)
                .get(GetAssignmentsResponse.class))
        .isNotNull()
        .isInstanceOf(GetAssignmentsResponse.class)
        .hasFieldOrPropertyWithValue(
            "assignments",
            ImmutableSet.of(
                new GetAssignmentResponse(
                    assignment,
                    0,
                    account,
                    task,
                    resource,
                    Instant.parse("2023-01-01T00:00:00Z"),
                    Instant.parse("2023-01-01T01:00:00Z"))));
  }

  @Test
  void post() {
    long account = repository.accounts().create(UUID.randomUUID().toString(), user);
    repository.accounts().createUserTokenForAccount(user, account, token);
    long task = repository.tasks().create(account, "tsk0003", "description");
    long resource = repository.resources().create(account, "rsc0003");

    CreateAssignmentResponse response =
        EXT.target("/assignments/%d".formatted(account))
            .request()
            .header(HttpHeaders.AUTHORIZATION, token)
            .post(
                Entity.json(
                    new CreateAssignmentRequest(
                        task,
                        resource,
                        Instant.parse("2023-02-04T05:00:00Z"),
                        Instant.parse("2023-02-04T06:00:00Z"))))
            .readEntity(CreateAssignmentResponse.class);

    Assertions.assertThat(repository.assignments().findById(account, response.id()))
        .isPresent()
        .get()
        .isInstanceOf(AssignmentView.class)
        .hasFieldOrPropertyWithValue("id", response.id())
        .hasFieldOrPropertyWithValue("revision", 0)
        .hasFieldOrPropertyWithValue("account", account)
        .hasFieldOrPropertyWithValue("task", task)
        .hasFieldOrPropertyWithValue("resource", resource)
        .hasFieldOrPropertyWithValue("start", Instant.parse("2023-02-04T05:00:00Z"))
        .hasFieldOrPropertyWithValue("end", Instant.parse("2023-02-04T06:00:00Z"));
  }
}
