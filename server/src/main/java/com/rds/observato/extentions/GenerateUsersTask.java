package com.rds.observato.extentions;

import com.rds.observato.api.persistence.Repository;
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
    repository.accounts().create("gadi", "gadi@rds.com");
    repository.accounts().create("rita", "rida@rds.com");
    repository.accounts().create("hilla", "hilla@rds.com");
    repository.accounts().create("tamara", "tamara@rds.com");
  }
}
