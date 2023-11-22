package com.rds.observato.tasks;

import com.rds.observato.ControllerBaseTest;
import com.rds.observato.auth.Role;
import io.dropwizard.testing.junit5.ResourceExtension;
import jakarta.ws.rs.core.HttpHeaders;
import java.util.UUID;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class TaskControllerTest extends ControllerBaseTest {

  private final ResourceExtension EXT = ext(() -> new TaskController(repository));

  @Test
  void getById() {
    long account = repository.accounts().create(UUID.randomUUID().toString(), user);
    long task = repository.tasks().create(account, "tsk0001", "description");
    repository.accounts().createUserTokenForAccount(user, account, "secret");
    repository.accounts().assignUserToAccount(user, account, Role.ADMIN);

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
