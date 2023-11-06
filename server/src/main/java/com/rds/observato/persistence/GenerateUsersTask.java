package com.rds.observato.persistence;

import com.rds.observato.api.persistence.Repository;
import com.rds.observato.api.request.CreateAccountRequest;
import io.dropwizard.servlets.tasks.Task;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GenerateUsersTask extends Task {
  private static final Logger log = LoggerFactory.getLogger(GenerateUsersTask.class);
  private final Repository repository;

  public GenerateUsersTask(Repository repository) {
    super("users");
    this.repository = repository;
  }

  @Override
  public void execute(Map<String, List<String>> parameters, PrintWriter output) throws Exception {
    repository
        .createAccount(new CreateAccountRequest("gadi", "gadi@rds.com"))
        .peek(id -> log.info("Account: {}", id))
        .peekLeft(error -> log.warn("Failed: {}", error));
    repository
        .createAccount(new CreateAccountRequest("tamara", "tamara@rds.com"))
        .peek(id -> log.info("Account: {}", id))
        .peekLeft(error -> log.warn("Failed: {}", error));
    repository
        .createAccount(new CreateAccountRequest("hilla", "hilla@rds.com"))
        .peek(id -> log.info("Account: {}", id))
        .peekLeft(error -> log.warn("Failed: {}", error));
    repository
        .createAccount(new CreateAccountRequest("rita", "rida@rds.com"))
        .peek(id -> log.info("Account: {}", id))
        .peekLeft(error -> log.warn("Failed: {}", error));
  }
}
