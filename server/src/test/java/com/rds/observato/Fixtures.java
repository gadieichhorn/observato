package com.rds.observato;

import com.github.javafaker.Faker;
import com.rds.observato.api.persistence.Repository;
import java.util.UUID;

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
}
