package com.rds.observato.persistence;

import com.rds.observato.api.persistence.Repository;
import com.rds.observato.persistence.accounts.AccountDao;
import com.rds.observato.persistence.accounts.AccountView;
import com.rds.observato.persistence.users.LoginView;
import com.rds.observato.persistence.users.UserView;
import com.rds.observato.persistence.users.UsersDao;
import org.jdbi.v3.core.Jdbi;
import org.jdbi.v3.core.mapper.reflect.ConstructorMapper;
import org.jdbi.v3.guava.GuavaPlugin;
import org.jdbi.v3.jackson2.Jackson2Plugin;
import org.jdbi.v3.postgres.PostgresPlugin;
import org.jdbi.v3.sqlobject.SqlObjectPlugin;

public record RepositoryDao(AccountDao accounts, UsersDao users) implements Repository {

  public static Repository create(Jdbi jdbi) {
    jdbi.installPlugin(new PostgresPlugin())
        .installPlugin(new SqlObjectPlugin())
        .installPlugin(new Jackson2Plugin())
        .installPlugin(new GuavaPlugin())
        .registerRowMapper(ConstructorMapper.factory(UserView.class))
        .registerRowMapper(ConstructorMapper.factory(LoginView.class))
        .registerRowMapper(ConstructorMapper.factory(AccountView.class));

    return new RepositoryDao(jdbi.onDemand(AccountDao.class), jdbi.onDemand(UsersDao.class));
  }
}
