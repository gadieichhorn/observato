package com.rds.observato.projects;

import com.rds.observato.DatabaseTestBase;
import com.rds.observato.api.persistence.Repository;
import com.rds.observato.api.response.GetProjectResponse;
import io.dropwizard.testing.junit5.DropwizardExtensionsSupport;
import io.dropwizard.testing.junit5.ResourceExtension;
import jakarta.ws.rs.core.HttpHeaders;
import java.util.UUID;
import org.assertj.core.api.Assertions;
import org.glassfish.jersey.test.grizzly.GrizzlyWebTestContainerFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(DropwizardExtensionsSupport.class)
class ProjectControllerTest extends DatabaseTestBase {

  private static Repository repository = repository();
  public static final ResourceExtension EXT =
      ResourceExtension.builder()
          .setTestContainerFactory(new GrizzlyWebTestContainerFactory())
          .addProvider(() -> new ProjectController(repository))
          .build();

  static long account;
  static long project;

  @BeforeAll
  static void setUp() {
    long user =
        repository
            .users()
            .create(UUID.randomUUID().toString(), "salt".getBytes(), "hash".getBytes());
    account = repository.accounts().create(UUID.randomUUID().toString(), user);
    project = repository.projects().create(account, "prj0001", "description");
  }

  @Test
  void get() {
    Assertions.assertThat(
            EXT.target("/projects/%d/%d".formatted(account, project))
                .request()
                .header(HttpHeaders.AUTHORIZATION, "secret")
                .get(GetProjectResponse.class))
        .isNotNull()
        .isInstanceOf(GetProjectResponse.class)
        .hasFieldOrPropertyWithValue("id", project)
        .hasFieldOrPropertyWithValue("name", "prj0001");
  }
}
