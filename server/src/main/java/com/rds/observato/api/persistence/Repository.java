package com.rds.observato.api.persistence;

import com.rds.observato.api.Error;
import com.rds.observato.api.request.CreateAccountRequest;
import com.rds.observato.persistence.view.AccountView;
import io.vavr.control.Either;
import java.util.Optional;

public interface Repository {

  Either<Error, Long> createAccount(CreateAccountRequest request);

  Either<Error, Optional<AccountView>> findAccountById(long id);
}
