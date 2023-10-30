package com.rds.observato.api.persistence;

import com.rds.observato.api.model.Account;
import com.rds.observato.api.model.Agent;
import com.rds.observato.api.model.Assignment;
import com.rds.observato.api.model.Task;
import io.vavr.control.Either;
import java.util.Optional;

public interface Repository {

  String key(Account account);

  String key(Account account, Task task);

  String key(Account account, Agent agent);

  String key(Account account, Assignment assignment);

  Either<java.lang.Error, Task> create(Task task);

  Either<java.lang.Error, Optional<Task>> find(String id);
}
