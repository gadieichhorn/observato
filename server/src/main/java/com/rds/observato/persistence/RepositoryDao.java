package com.rds.observato.persistence;

import com.rds.observato.api.Error;
import com.rds.observato.api.persistence.Repository;
import com.rds.observato.api.request.CreateAccountRequest;
import com.rds.observato.persistence.view.AccountView;
import io.vavr.control.Either;
import io.vavr.control.Try;
import java.util.Optional;
import org.jdbi.v3.core.Jdbi;
import org.jdbi.v3.core.mapper.reflect.ConstructorMapper;
import org.jdbi.v3.guava.GuavaPlugin;
import org.jdbi.v3.jackson2.Jackson2Plugin;
import org.jdbi.v3.postgres.PostgresPlugin;
import org.jdbi.v3.sqlobject.SqlObjectPlugin;

public record RepositoryDao(AccountDao accounts) implements Repository {

  public static Repository create(Jdbi jdbi) {
    jdbi.installPlugin(new PostgresPlugin())
        .installPlugin(new SqlObjectPlugin())
        .installPlugin(new Jackson2Plugin())
        .installPlugin(new GuavaPlugin())
        .registerRowMapper(ConstructorMapper.factory(AccountView.class));

    return new RepositoryDao(jdbi.onDemand(AccountDao.class));
  }

  @Override
  public Either<Error, Long> createAccount(CreateAccountRequest request) {
    return Try.of(() -> accounts.create(request.name(), request.owner()))
        .toEither()
        .mapLeft(SqlError::from);
  }

  @Override
  public Either<Error, Optional<AccountView>> findAccountById(long id) {
    return Try.of(() -> accounts.findById(id)).toEither().mapLeft(SqlError::from);
  }
}
