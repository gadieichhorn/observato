package com.rds.observato.extentions;

import com.rds.observato.api.persistence.Repository;
import com.rds.observato.auth.AuthService;
import com.rds.observato.auth.Roles;
import io.dropwizard.servlets.tasks.Task;
import java.io.PrintWriter;
import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Stream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GenerateDemoDataTask extends Task {

  private static final Logger log = LoggerFactory.getLogger(GenerateDemoDataTask.class);
  private final Repository repository;
  private final AuthService auth;

  public GenerateDemoDataTask(Repository repository, AuthService auth) {
    super("demo");
    this.repository = repository;
    this.auth = auth;
  }

  @Override
  public void execute(Map<String, List<String>> parameters, PrintWriter output) throws Exception {
    Stream.iterate(0, i -> i + 1)
        .limit(3)
        .map(
            i ->
                repository
                    .users()
                    .create(UUID.randomUUID().toString(), "salt".getBytes(), "hash".getBytes()))
        .peek(user -> log.info("USER: {}", user))
        .forEach(user -> account(user));
  }

  private void account(Long user) {
    Stream.iterate(0, i -> i + 1)
        .limit(3)
        .map(i -> repository.accounts().create(UUID.randomUUID().toString(), user))
        .peek(account -> log.info("ACCOUNT: {}", account))
        .peek(account -> repository.accounts().assignUserToAccount(user, account, Roles.ADMIN))
        .peek(
            account ->
                repository
                    .accounts()
                    .createUserTokenForAccount(user, account, UUID.randomUUID().toString().replace("-","")))
        .forEach(account -> resource(user, account));
  }

  private void resource(Long user, Long account) {
    Stream.iterate(0, i -> i + 1)
        .limit(5)
        .map(i -> repository.resources().create(account, UUID.randomUUID().toString()))
        .peek(resource -> log.info("RESOURCE: {}", resource))
        .forEach(resource -> task(user, account, resource));
  }

  private void task(Long user, Long account, Long resource) {
    long project = repository.projects().create(account, UUID.randomUUID().toString(), "project");
    log.info("PROJECT: {}", project);
    Stream.iterate(0, i -> i + 1)
        .limit(5)
        .map(
            i ->
                repository
                    .tasks()
                    .create(account, UUID.randomUUID().toString(), "task%d".formatted(i)))
        .peek(task -> log.info("TASK: {}", task))
        .peek(task -> repository.projects().assignTaskToProject(account, task, project))
        .map(task -> assignment(user, account, resource, task))
        .forEach(assignment -> log.info("ASSIGNMENT: {}", assignment));
  }

  private long assignment(Long user, Long account, Long resource, Long task) {
    return repository
        .assignments()
        .create(
            account,
            task,
            resource,
            Instant.now().plusSeconds(task * 1000),
            Instant.now().plusSeconds((task + 1) * 1000));
  }
}
