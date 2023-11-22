package com.rds.observato.projects;

import com.google.common.collect.ImmutableSet;
import com.rds.observato.ControllerBaseTest;
import com.rds.observato.api.response.GetProjectResponse;
import com.rds.observato.api.response.GetProjectsResponse;
import com.rds.observato.auth.Role;
import io.dropwizard.testing.junit5.ResourceExtension;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.core.HttpHeaders;
import java.util.UUID;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class ProjectsControllerTest extends ControllerBaseTest {

  private final ResourceExtension EXT = ext(() -> new ProjectsController(repository));

  @Test
  void get() {
    long account = repository.accounts().create(UUID.randomUUID().toString(), user);
    long project = repository.projects().create(account, "prj0002", "description");
    repository.accounts().createUserTokenForAccount(user, account, token);
    repository.accounts().assignUserToAccount(user, account, Role.ADMIN);

    Assertions.assertThat(
            EXT.target("/projects/%d".formatted(account))
                .request()
                .header(HttpHeaders.AUTHORIZATION, token)
                .get(GetProjectsResponse.class))
        .isNotNull()
        .isInstanceOf(GetProjectsResponse.class)
        .hasFieldOrPropertyWithValue(
            "projects",
            ImmutableSet.of(new GetProjectResponse(project, account, "prj0002", "description")));
  }

  @Test
  void post() {
    long account = repository.accounts().create(UUID.randomUUID().toString(), user);
    repository.accounts().createUserTokenForAccount(user, account, token);
    repository.accounts().assignUserToAccount(user, account, Role.ADMIN);

    CreateProjectResponse response =
        EXT.target("/projects/%d".formatted(account))
            .request()
            .header(HttpHeaders.AUTHORIZATION, token)
            .post(Entity.json(new CreateProjectRequest("prj00003", "description")))
            .readEntity(CreateProjectResponse.class);

    Assertions.assertThat(repository.projects().findById(account, response.id()))
        .isPresent()
        .get()
        .isInstanceOf(ProjectView.class)
        .hasFieldOrPropertyWithValue("name", "prj00003")
        .hasFieldOrPropertyWithValue("description", "description");
  }
}
