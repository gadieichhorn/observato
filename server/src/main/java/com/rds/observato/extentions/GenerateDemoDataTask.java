package com.rds.observato.extentions;

import com.rds.observato.api.persistence.Repository;
import com.rds.observato.auth.AuthService;
import io.dropwizard.servlets.tasks.Task;
import io.vavr.control.Either;
import io.vavr.control.Try;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;
import java.util.UUID;
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
    user()
        .flatMap(this::account)
        .flatMap(this::tasks)
        .peekLeft(
            throwable -> log.warn("Failed to create user with error: {}", throwable.message()));
  }

  private Either<Error, Long> user() {
    String password = UUID.randomUUID().toString().replace("-", "");
    System.out.println(password);
    byte[] salt = auth.salt();

    return Try.of(() -> auth.hash(salt, password))
        .map(hash -> repository.users().create("gadi", salt, hash))
        .toEither()
        .mapLeft(SqlError::from);
  }

  private Either<Error, Long> account(long user) {
    return Try.of(() -> repository.accounts().create("acc001", user))
        .toEither()
        .peek(account -> log.info("Account created: {}", account))
        .mapLeft(SqlError::from);
  }

  private Either<Error, Long> tasks(long account) {
    return Try.of(() -> repository.tasks().create(account, "tsk001", "a task"))
        .toEither()
        .peek(task -> log.info("Task created: {} -> {}", account, task))
        .mapLeft(SqlError::from);
  }
}
