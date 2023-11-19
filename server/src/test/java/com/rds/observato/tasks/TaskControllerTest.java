package com.rds.observato.tasks;

import static org.junit.jupiter.api.Assertions.*;

import com.rds.observato.DatabaseTestBase;
import com.rds.observato.api.persistence.Repository;
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
class TaskControllerTest extends DatabaseTestBase {

  private static Repository repository = repository();
  public static final ResourceExtension EXT =
      ResourceExtension.builder()
          .setTestContainerFactory(new GrizzlyWebTestContainerFactory())
          .addProvider(() -> new TaskController(repository))
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
  void getById() {
    long account = repository.accounts().create(UUID.randomUUID().toString(), user);
    long task = repository.tasks().create(account, "tsk0001", "description");

    Assertions.assertThat(
            EXT.target("/tasks/%d/%d".formatted(account, task))
                .request()
                .header(HttpHeaders.AUTHORIZATION, "secret")
                .get(GetTaskResponse.class))
        .isNotNull()
        .isInstanceOf(GetTaskResponse.class)
        .hasFieldOrPropertyWithValue("id", task)
        .hasFieldOrPropertyWithValue("name", "tsk0001");
  }
}
