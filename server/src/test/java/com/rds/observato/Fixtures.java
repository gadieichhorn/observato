package com.rds.observato;

import com.rds.observato.api.persistence.Repository;
import com.rds.observato.auth.Role;
import com.rds.observato.auth.User;
import java.util.UUID;
import org.testcontainers.shaded.com.google.common.collect.ImmutableSet;

public class Fixtures {

  public static String id() {
    return UUID.randomUUID().toString().replace("-", "");
  }

  public static long createUser(Repository repository) {
    return repository.users().create(id(), "salt".getBytes(), "hash".getBytes());
  }

  public static long createAccount(Repository repository, long user) {
    return repository.accounts().create(id(), user);
  }

  public static long createProject(Repository repository, long account) {
    return repository.projects().create(account, id(), id());
  }

  public static long createTask(Repository repository, long account) {
    return repository.tasks().create(account, id(), id());
  }

  public static long createResource(Repository repository, long account) {
    return repository.resources().create(account, id());
  }

  public static String token(long user) {
    return id().replace("-", "");
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
