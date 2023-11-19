package com.rds.observato.assignments;

import com.rds.observato.DatabaseTestBase;
import com.rds.observato.api.persistence.Repository;
import io.dropwizard.testing.junit5.DropwizardExtensionsSupport;
import io.dropwizard.testing.junit5.ResourceExtension;
import jakarta.ws.rs.core.HttpHeaders;
import java.time.Instant;
import java.util.UUID;
import org.assertj.core.api.Assertions;
import org.glassfish.jersey.test.grizzly.GrizzlyWebTestContainerFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(DropwizardExtensionsSupport.class)
class AssignmentControllerTest extends DatabaseTestBase {

  private static Repository repository = repository();
  public static final ResourceExtension EXT =
      ResourceExtension.builder()
          .setTestContainerFactory(new GrizzlyWebTestContainerFactory())
          .addProvider(() -> new AssignmentController(repository))
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
    long task = repository.tasks().create(account, "tsk0004", "description");
    long resource = repository.resources().create(account, "rsc0004");
    long assignment =
        repository
            .assignments()
            .create(
                account,
                task,
                resource,
                Instant.parse("2024-01-01T00:00:00Z"),
                Instant.parse("2024-01-01T01:00:00Z"));

    Assertions.assertThat(
            EXT.target("/assignments/%d/%d".formatted(account, assignment))
                .request()
                .header(HttpHeaders.AUTHORIZATION, "secret")
                .get(GetAssignmentResponse.class))
        .isNotNull()
        .isInstanceOf(GetAssignmentResponse.class)
        .hasFieldOrPropertyWithValue("id", assignment)
        .hasFieldOrPropertyWithValue("revision", 0)
        .hasFieldOrPropertyWithValue("task", task)
        .hasFieldOrPropertyWithValue("resource", resource)
        .hasFieldOrPropertyWithValue("account", account)
        .hasFieldOrPropertyWithValue("start", Instant.parse("2024-01-01T00:00:00Z"))
        .hasFieldOrPropertyWithValue("end", Instant.parse("2024-01-01T01:00:00Z"));
  }
}
