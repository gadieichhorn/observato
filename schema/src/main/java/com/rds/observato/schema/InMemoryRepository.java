package com.rds.observato.schema;

import com.rds.observato.api.model.Account;
import com.rds.observato.api.model.Agent;
import com.rds.observato.api.model.Assignment;
import com.rds.observato.api.model.Task;
import com.rds.observato.api.persistence.Repository;
import io.vavr.control.Either;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

public record InMemoryRepository(Map<String, Agent> agents) implements Repository {

  private static AtomicLong accountCounter = new AtomicLong(0);
  private static AtomicLong agentCounter = new AtomicLong(0);

  private static HashIdGenerator accountIdGenerator = HashIdGenerator.create("account");

  @Override
  public String key(Account account) {
    return "ACC-%s".formatted(accountIdGenerator.encode(account.id()));
  }

  @Override
  public String key(Account account, Task task) {
    return null;
  }

  @Override
  public String key(Account account, Agent agent) {
    String formatted =
        "AG-%s-%s"
            .formatted(account.key(), accountIdGenerator.encode(accountCounter.incrementAndGet()));
    return formatted;
  }

  @Override
  public String key(Account account, Assignment assignment) {
    return null;
  }

  @Override
  public Either<Error, Task> create(Task task) {
    return null;
  }

  @Override
  public Either<Error, Optional<Task>> find(String id) {
    return null;
  }
}
