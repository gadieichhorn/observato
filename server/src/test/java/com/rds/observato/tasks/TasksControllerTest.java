package com.rds.observato.tasks;

import com.rds.observato.ControllerBaseTest;
import com.rds.observato.auth.Role;
import io.dropwizard.testing.junit5.DropwizardExtensionsSupport;
import io.dropwizard.testing.junit5.ResourceExtension;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.core.HttpHeaders;
import java.util.UUID;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testcontainers.shaded.com.google.common.collect.ImmutableMap;
import org.testcontainers.shaded.com.google.common.collect.ImmutableSet;

@ExtendWith(DropwizardExtensionsSupport.class)
class TasksControllerTest extends ControllerBaseTest {

  private final ResourceExtension EXT = ext(() -> new TasksController(repository));

  @Test
  void create() {
    long account = repository.accounts().create(UUID.randomUUID().toString(), user);
    repository.accounts().createUserTokenForAccount(user, account, token);
    repository.accounts().assignUserToAccount(user, account, Role.ADMIN);

    CreateTaskResponse response =
        EXT.target("/tasks")
            .request()
            .header(HttpHeaders.AUTHORIZATION, token)
            .post(Entity.json(new CreateTaskRequest("tsk0002", "description")))
            .readEntity(CreateTaskResponse.class);

    Assertions.assertThat(repository.tasks().finById(account, response.id()))
        .isPresent()
        .get()
        .isInstanceOf(TaskRecord.class)
        .hasFieldOrPropertyWithValue("id", response.id());
  }

  @Test
  void get() {
    long account = repository.accounts().create(UUID.randomUUID().toString(), user);
    long task = repository.tasks().create(account, "tsk0001", "description");
    repository.accounts().createUserTokenForAccount(user, account, token);
    repository.accounts().assignUserToAccount(user, account, Role.ADMIN);

    Assertions.assertThat(
            EXT.target("/tasks")
                .request()
                .header(HttpHeaders.AUTHORIZATION, token)
                .get(GetTasksResponse.class))
        .hasFieldOrPropertyWithValue(
            "tasks",
            ImmutableSet.of(
                new GetTaskResponse(
                    task, 0, account, "tsk0001", "description", ImmutableMap.of())));
  }
}
