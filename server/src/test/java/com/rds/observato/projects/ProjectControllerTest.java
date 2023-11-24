package com.rds.observato.projects;

import com.rds.observato.ControllerBaseTest;
import com.rds.observato.auth.Role;
import io.dropwizard.testing.junit5.ResourceExtension;
import jakarta.ws.rs.core.HttpHeaders;
import java.util.UUID;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class ProjectControllerTest extends ControllerBaseTest {

  private final ResourceExtension EXT = ext(() -> new ProjectController(repository));

  @Test
  void get() {
    long account = repository.accounts().create(UUID.randomUUID().toString(), user);
    long project = repository.projects().create(account, "prj0001", "description");
    repository.accounts().createUserTokenForAccount(user, account, token);
    repository.accounts().assignUserToAccount(user, account, Role.ADMIN);

    Assertions.assertThat(
            EXT.target("/projects/%d".formatted(project))
                .request()
                .header(HttpHeaders.AUTHORIZATION, token)
                .get(GetProjectResponse.class))
        .isNotNull()
        .isInstanceOf(GetProjectResponse.class)
        .hasFieldOrPropertyWithValue("id", project)
        .hasFieldOrPropertyWithValue("name", "prj0001");
  }
}
