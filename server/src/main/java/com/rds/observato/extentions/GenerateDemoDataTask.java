package com.rds.observato.extentions;

import com.github.javafaker.Faker;
import com.rds.observato.auth.AuthService;
import com.rds.observato.auth.Role;
import com.rds.observato.db.Repository;
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

  private final Faker faker = new Faker();

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
                    .create(faker.internet().emailAddress(), "salt".getBytes(), "hash".getBytes()))
        .peek(user -> log.info("USER: {}", user))
        .forEach(this::account);
  }

  private void account(Long user) {
    Stream.iterate(0, i -> i + 1)
        .limit(3)
        .map(i -> repository.accounts().create(faker.company().name(), user))
        .peek(account -> log.info("ACCOUNT: {}", account))
        .peek(account -> repository.accounts().assignUserToAccount(user, account, Role.ADMIN))
        .peek(this::skills)
        .peek(
            account ->
                repository
                    .accounts()
                    .createUserTokenForAccount(user, account, faker.random().hex(32)))
        .forEach(this::resource);
  }

  private void skills(long account) {
    Stream.iterate(0, i -> i + 1)
        .limit(10)
        .map(i -> faker.superhero())
        .forEach(
            superhero ->
                repository.skills().create(account, superhero.name(), superhero.descriptor()));
  }

  private void resource(Long account) {
    Stream.iterate(0, i -> i + 1)
        .limit(5)
        .map(i -> repository.resources().create(account, faker.name().fullName()))
        .peek(resource -> log.info("RESOURCE: {}", resource))
        .forEach(resource -> task(account, resource));
  }

  private void task(Long account, Long resource) {
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
        .map(task -> assignment(account, resource, task))
        .forEach(assignment -> log.info("ASSIGNMENT: {}", assignment));
  }

  private long assignment(Long account, Long resource, Long task) {
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
