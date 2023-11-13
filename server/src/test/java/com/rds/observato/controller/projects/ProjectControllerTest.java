package com.rds.observato.controller.projects;

import com.rds.observato.DatabaseTestBase;
import com.rds.observato.api.persistence.Repository;
import io.dropwizard.testing.junit5.DropwizardExtensionsSupport;
import io.dropwizard.testing.junit5.ResourceExtension;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.Response;
import java.util.UUID;
import org.glassfish.jersey.test.grizzly.GrizzlyWebTestContainerFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(DropwizardExtensionsSupport.class)
class ProjectControllerTest extends DatabaseTestBase {

  private static Repository repository = repository();
  public static final ResourceExtension EXT =
      ResourceExtension.builder()
          //          .addProvider(RolesAllowedDynamicFeature.class)
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
    project = repository.projects().create(account, UUID.randomUUID().toString(), "description");
  }

  @Test
  void get() {
    //        String credential = "Basic " +
    // Base64.getEncoder().encodeToString("internal:secret".getBytes());
    Response response =
        EXT.target("/projects/%d/%d".formatted(account, project))
            .request()
            .header(HttpHeaders.AUTHORIZATION, "secret")
            .get();
    //    Assertions.assertThat(results).isPresent().get().isEqualTo("cd5");
  }
}
