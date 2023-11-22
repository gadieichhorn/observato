package com.rds.observato;

import com.github.javafaker.Faker;
import com.rds.observato.api.persistence.Repository;
import com.rds.observato.auth.Role;
import com.rds.observato.auth.User;
import java.util.UUID;
import org.testcontainers.shaded.com.google.common.collect.ImmutableSet;

public class Fixtures {

  private static final Faker faker = new Faker();

  public static long createUser(Repository repository) {
    return repository
        .users()
        .create(faker.idNumber().valid(), "salt".getBytes(), "hash".getBytes());
  }

  public static long createAccount(Repository repository, long user) {
    return repository.accounts().create(UUID.randomUUID().toString(), user);
  }

  public static long createProject(Repository repository, long account) {
    return repository
        .projects()
        .create(account, faker.name().firstName(), faker.lorem().paragraph());
  }

  public static long createTask(Repository repository, long account) {
    return repository.tasks().create(account, faker.name().lastName(), faker.lorem().paragraph());
  }

  public static long createResource(Repository repository, long account) {
    return repository.resources().create(account, faker.name().username());
  }

  public static String token(long user) {
    return UUID.randomUUID().toString().replace("-", "");
  }

  public static User admin() {
    return new User(0, "admin", ImmutableSet.of(Role.ADMIN));
  }

  public static User scheduler() {
    return new User(0, "scheduler", ImmutableSet.of(Role.SCHEDULER));
  }

  public static User resource() {
    return new User(0, "resource", ImmutableSet.of(Role.RESOURCE));
  }

  public static User anonymous() {
    return new User(0, "anonymous", ImmutableSet.of(Role.ANONYMOUS));
  }

  public static User user(Role role, long user) {
    return new User(user, "user", ImmutableSet.of(role));
  }
}
