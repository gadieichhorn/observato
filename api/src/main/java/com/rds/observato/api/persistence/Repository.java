package com.rds.observato.api.persistence;

import com.rds.observato.api.Error;
import com.rds.observato.api.model.Account;
import com.rds.observato.api.request.CreateAccountRequest;
import io.vavr.control.Either;

public interface Repository {

  //  Either<Error, String> key(Account account);
  //
  //  Either<Error, String> key(Account account, Task task);
  //
  //  Either<Error, String> key(Account account, Agent agent);
  //
  //  Either<Error, String> key(Account account, Assignment assignment);
  //
  //  Either<Error, Task> create(Task task);

  //  Either<Error, Optional<Task>> find(String id);

  Either<Error, Account> create(CreateAccountRequest request);
}
