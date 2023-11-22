package com.rds.observato;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import com.rds.observato.api.request.CreateAccountRequest;
import com.rds.observato.api.request.CreateTaskRequest;
import com.rds.observato.assignments.CreateAssignmentRequest;
import com.rds.observato.auth.Role;
import com.rds.observato.projects.CreateProjectRequest;
import io.dropwizard.testing.junit5.DropwizardAppExtension;
import io.dropwizard.testing.junit5.DropwizardExtensionsSupport;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.core.HttpHeaders;
import java.time.Clock;
import java.time.Instant;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ExtendWith(DropwizardExtensionsSupport.class)
class ObservatoApplicationTest extends DatabaseTestBase {
  private static final Logger log = LoggerFactory.getLogger(ObservatoApplicationTest.class);
  private static DropwizardAppExtension<ObservatoConfiguration> EXT =
      Fixtures.integrationExtension(DATABASE);

  private final long user = Fixtures.createUser(repository);
  private final String token = Fixtures.token(user);
  private final long account = Fixtures.createAccount(repository, user);
  private final long task = Fixtures.createTask(repository, account);
  private final long resource = Fixtures.createResource(repository, account);
  private Client client;

  private static final Clock CLOCK =
      Clock.fixed(Instant.parse("2020-03-05T00:00:00Z"), ZoneOffset.UTC);

  @BeforeEach
  void setUp() {

    repository.accounts().createUserTokenForAccount(user, account, token);
    repository.accounts().assignUserToAccount(user, account, Role.ADMIN);

    //    long assignment =
    //        repository
    //            .assignments()
    //            .create(
    //                account,
    //                task,
    //                resource,
    //                Instant.parse("2024-01-01T00:00:00Z"),
    //                Instant.parse("2024-01-01T01:00:00Z"));

    client = EXT.client();
  }

  @Test
  void createAccount() {
    assertThat(
            client
                .target(String.format("http://localhost:%d/api/accounts", EXT.getLocalPort()))
                .request()
                .header(HttpHeaders.AUTHORIZATION, token)
                .post(Entity.json(new CreateAccountRequest("test", 1)))
                .getStatus())
        .isEqualTo(200);
  }

  @Test
  void createProject() {
    assertThat(
            client
                .target(
                    String.format(
                        "http://localhost:%d/api/projects/%d", EXT.getLocalPort(), account))
                .request()
                .header(HttpHeaders.AUTHORIZATION, token)
                .post(Entity.json(new CreateProjectRequest("test", "descr")))
                .getStatus())
        .isEqualTo(200);
  }

  @Test
  void createTask() {
    assertThat(
            client
                .target(
                    String.format("http://localhost:%d/api/tasks/%d", EXT.getLocalPort(), account))
                .request()
                .header(HttpHeaders.AUTHORIZATION, token)
                .post(Entity.json(new CreateTaskRequest("tsk011", "descr")))
                .getStatus())
        .isEqualTo(200);
  }

  @Test
  void createAssignment() {
    Instant start = CLOCK.instant();
    Instant end = start.plus(1, ChronoUnit.HOURS);

    assertThat(
            client
                .target(
                    String.format(
                        "http://localhost:%d/api/assignments/%d", EXT.getLocalPort(), account))
                .request()
                .header(HttpHeaders.AUTHORIZATION, token)
                .post(Entity.json(new CreateAssignmentRequest(task, resource, start, end)))
                .getStatus())
        .isEqualTo(200);
  }
}
