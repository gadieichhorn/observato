package com.rds.observato.tasks;

import com.rds.observato.DatabaseTestBase;
import com.rds.observato.api.persistence.Repository;
import com.rds.observato.api.request.CreateTaskRequest;
import com.rds.observato.api.response.CreateTaskResponse;
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
  public static final ResourceExtension EXT =
      ResourceExtension.builder()
          .setTestContainerFactory(new GrizzlyWebTestContainerFactory())
          .addProvider(() -> new TasksController(repository))
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
  void create() {
    long account = repository.accounts().create(UUID.randomUUID().toString(), user);

    CreateTaskResponse response =
        EXT.target("/tasks/%d".formatted(account))
            .request()
            .header(HttpHeaders.AUTHORIZATION, "secret")
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

    Assertions.assertThat(
            EXT.target("/tasks/%d".formatted(account))
                .request()
                .header(HttpHeaders.AUTHORIZATION, "secret")
                .get(GetTasksResponse.class))
        .hasFieldOrPropertyWithValue(
            "tasks",
            ImmutableSet.of(new GetTaskResponse(task, 0, account, "tsk0001", "description")));
  }
}
