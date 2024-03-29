package com.rds.observato.tasks;

import com.rds.observato.ControllerBaseTest;
import com.rds.observato.auth.Role;
import com.rds.observato.view.TaskView;
import io.dropwizard.testing.junit5.ResourceExtension;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.MediaType;
import java.util.UUID;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testcontainers.shaded.com.google.common.collect.ImmutableMap;

class TaskControllerTest extends ControllerBaseTest {

  private final ResourceExtension EXT = ext(() -> new TaskController(repository));

  long account;
  long task;

  @BeforeEach
  void setUp() {
    account = repository.accounts().create(UUID.randomUUID().toString(), user);
    task = repository.tasks().create(account, "tsk0001", "description");
    repository.accounts().createUserTokenForAccount(user, account, token);
    repository.accounts().assignUserToAccount(user, account, Role.ADMIN);
  }

  @Test
  void get() {
    Assertions.assertThat(
            EXT.target("/tasks/%d".formatted(task))
                .request()
                .header(HttpHeaders.AUTHORIZATION, token)
                .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON)
                .get(TaskView.class))
        .isNotNull()
        .isInstanceOf(TaskView.class)
        .hasFieldOrPropertyWithValue(
            "task", new TaskRecord(task, 0, account, "tsk0001", "description", ImmutableMap.of()));
  }

  @Test
  void put() {
    Assertions.assertThat(
            EXT.target("/tasks/%d".formatted(task))
                .request()
                .header(HttpHeaders.AUTHORIZATION, token)
                .put(Entity.json(new UpdateTaskRequest(task, 0, "tsk0002", "updated")))
                .readEntity(UpdateTaskResponse.class))
        .isNotNull()
        .isInstanceOf(UpdateTaskResponse.class)
        .hasFieldOrPropertyWithValue("changed", 1);
  }
}
