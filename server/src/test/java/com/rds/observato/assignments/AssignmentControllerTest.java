package com.rds.observato.assignments;

import com.rds.observato.ControllerBaseTest;
import com.rds.observato.auth.Role;
import io.dropwizard.testing.junit5.ResourceExtension;
import jakarta.ws.rs.core.HttpHeaders;
import java.time.Instant;
import java.util.UUID;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class AssignmentControllerTest extends ControllerBaseTest {

  private final ResourceExtension EXT = ext(() -> new AssignmentController(repository));

  @Test
  void get() {
    long account = repository.accounts().create(UUID.randomUUID().toString(), user);
    repository.accounts().createUserTokenForAccount(user, account, token);
    repository.accounts().assignUserToAccount(user, account, Role.ADMIN);

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
                .header(HttpHeaders.AUTHORIZATION, token)
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
