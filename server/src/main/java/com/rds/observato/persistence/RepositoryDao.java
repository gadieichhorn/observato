package com.rds.observato.persistence;

import com.rds.observato.api.persistence.Repository;
import com.rds.observato.persistence.accounts.AccountDao;
import com.rds.observato.persistence.accounts.AccountView;
import com.rds.observato.persistence.accounts.UserAccountView;
import com.rds.observato.persistence.assignments.AssignmentDao;
import com.rds.observato.persistence.projects.ProjectView;
import com.rds.observato.persistence.projects.ProjectsDao;
import com.rds.observato.persistence.resources.ResourceView;
import com.rds.observato.persistence.resources.ResourcesDao;
import com.rds.observato.persistence.tasks.TaskView;
import com.rds.observato.persistence.tasks.TasksDao;
import com.rds.observato.persistence.users.LoginView;
import com.rds.observato.persistence.users.UserView;
import com.rds.observato.persistence.users.UsersDao;
import com.rds.observato.validation.Validator;
import org.jdbi.v3.core.Jdbi;
import org.jdbi.v3.core.mapper.reflect.ConstructorMapper;
import org.jdbi.v3.guava.GuavaPlugin;
import org.jdbi.v3.jackson2.Jackson2Plugin;
import org.jdbi.v3.postgres.PostgresPlugin;
import org.jdbi.v3.sqlobject.SqlObjectPlugin;

public record RepositoryDao(
    AccountDao accounts,
    UsersDao users,
    ProjectsDao projects,
    TasksDao tasks,
    ResourcesDao resources,
    AssignmentDao assignments)
    implements Repository {

  public RepositoryDao {
    Validator.checkIsNull(users, "users");
    Validator.checkIsNull(accounts, "accounts");
    Validator.checkIsNull(tasks, "tasks");
    Validator.checkIsNull(projects, "projects");
    Validator.checkIsNull(resources, "resources");
    Validator.checkIsNull(assignments, "assignments");
  }

  public static Repository create(Jdbi jdbi) {
    jdbi.installPlugin(new PostgresPlugin())
        .installPlugin(new SqlObjectPlugin())
        .installPlugin(new Jackson2Plugin())
        .installPlugin(new GuavaPlugin())
        .registerRowMapper(ConstructorMapper.factory(TaskView.class))
        .registerRowMapper(ConstructorMapper.factory(UserView.class))
        .registerRowMapper(ConstructorMapper.factory(LoginView.class))
        .registerRowMapper(ConstructorMapper.factory(ProjectView.class))
        .registerRowMapper(ConstructorMapper.factory(AccountView.class))
        .registerRowMapper(ConstructorMapper.factory(ResourceView.class))
        .registerRowMapper(ConstructorMapper.factory(UserAccountView.class));

    return new RepositoryDao(
        jdbi.onDemand(AccountDao.class),
        jdbi.onDemand(UsersDao.class),
        jdbi.onDemand(ProjectsDao.class),
        jdbi.onDemand(TasksDao.class),
        jdbi.onDemand(ResourcesDao.class),
        jdbi.onDemand(AssignmentDao.class));
  }
}
