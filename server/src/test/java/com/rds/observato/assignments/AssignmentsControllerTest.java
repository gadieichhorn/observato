package com.rds.observato.assignments;

import com.rds.observato.ControllerBaseTest;
import com.rds.observato.auth.Role;
import io.dropwizard.testing.junit5.ResourceExtension;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.core.HttpHeaders;
import java.time.Instant;
import java.util.UUID;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.testcontainers.shaded.com.google.common.collect.ImmutableSet;

class AssignmentsControllerTest extends ControllerBaseTest {

  private final ResourceExtension EXT = ext(() -> new AssignmentsController(repository));

  @Test
  void get() {
    long account = repository.accounts().create(UUID.randomUUID().toString(), user);
    repository.accounts().createUserTokenForAccount(user, account, token);
    repository.accounts().assignUserToAccount(user, account, Role.ADMIN);

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
    repository.accounts().assignUserToAccount(user, account, Role.ADMIN);

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
