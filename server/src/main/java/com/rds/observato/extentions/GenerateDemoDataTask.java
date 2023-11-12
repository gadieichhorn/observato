package com.rds.observato.extentions;

import com.rds.observato.api.persistence.Repository;
import com.rds.observato.auth.AuthService;
import io.dropwizard.servlets.tasks.Task;
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
    String password = UUID.randomUUID().toString().replace("-", "");
    System.out.println(password);

    byte[] salt = auth.salt();
    byte[] hash = auth.hash(salt, password);

    Try.of(() -> repository.users().create("gadi", salt, hash))
        .toEither()
        .peek(id -> log.info("User [{}] created with password: {}", id, password))
        .peekLeft(
            throwable -> log.warn("Failed to create user with error: {}", throwable.getMessage()));

    Try.of(() -> repository.accounts().create("observato", "gadi@rds.com"))
        .toEither()
        .peekLeft(
            throwable ->
                log.warn("Failed to create account with error: {}", throwable.getMessage()));

    //    Try.of(()-> repository.accounts().)
  }
}
