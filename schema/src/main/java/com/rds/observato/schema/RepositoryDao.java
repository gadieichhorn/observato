package com.rds.observato.schema;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.guava.GuavaModule;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.rds.observato.api.Error;
import com.rds.observato.api.model.Account;
import com.rds.observato.api.persistence.Repository;
import com.rds.observato.api.request.CreateAccountRequest;
import io.vavr.control.Either;
import io.vavr.control.Try;
import org.jdbi.v3.core.Jdbi;
import org.jdbi.v3.guava.GuavaPlugin;
import org.jdbi.v3.jackson2.Jackson2Config;
import org.jdbi.v3.jackson2.Jackson2Plugin;
import org.jdbi.v3.postgres.PostgresPlugin;
import org.jdbi.v3.sqlobject.SqlObjectPlugin;

public record RepositoryDao(AccountDao accounts) implements Repository {

  public static Repository create(Jdbi jdbi) {

    JsonMapper mapper =
        JsonMapper.builder()
            .addModule(new GuavaModule())
            .addModule(new Jdk8Module())
            .addModule(new JavaTimeModule())
            .registerSubtypes(ImmutableMap.class)
            .registerSubtypes(ImmutableList.class)
            .registerSubtypes(ImmutableSet.class)
            .disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
            .disable(DeserializationFeature.WRAP_EXCEPTIONS)
            .disable(DeserializationFeature.ACCEPT_FLOAT_AS_INT)
            .disable(DeserializationFeature.ADJUST_DATES_TO_CONTEXT_TIME_ZONE)
            .enable(DeserializationFeature.FAIL_ON_NULL_FOR_PRIMITIVES)
            .enable(DeserializationFeature.FAIL_ON_NUMBERS_FOR_ENUMS)
            .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
            .disable(SerializationFeature.WRITE_DURATIONS_AS_TIMESTAMPS)
            .disable(SerializationFeature.FAIL_ON_EMPTY_BEANS)
            .disable(MapperFeature.USE_GETTERS_AS_SETTERS)
            //        .disable(MapperFeature.ALLOW_COERCION_OF_SCALARS)
            .serializationInclusion(JsonInclude.Include.NON_ABSENT)
            .build();

    jdbi.installPlugin(new GuavaPlugin())
        .installPlugin(new Jackson2Plugin())
        .installPlugin(new PostgresPlugin())
        .installPlugin(new SqlObjectPlugin())
        .getConfig(Jackson2Config.class)
        .setMapper(mapper);

    return new RepositoryDao(jdbi.onDemand(AccountDao.class));
  }

  @Override
  public Either<Error, Account> create(CreateAccountRequest request) {
    return Try.of(() -> accounts.create(request))
        .map(id -> new Account(id, request.name(), request.name()))
        .toEither()
        .mapLeft(SqlError::from);
  }
}
