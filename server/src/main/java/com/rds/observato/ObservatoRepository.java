package com.rds.observato;

import com.rds.observato.accounts.AccountView;
import com.rds.observato.accounts.AccountsDao;
import com.rds.observato.accounts.TokenView;
import com.rds.observato.accounts.UserAccountView;
import com.rds.observato.assignments.AssignmentDao;
import com.rds.observato.assignments.AssignmentView;
import com.rds.observato.projects.ProjectView;
import com.rds.observato.projects.ProjectsDao;
import com.rds.observato.resources.ResourceView;
import com.rds.observato.resources.ResourcesDao;
import com.rds.observato.skills.SkillView;
import com.rds.observato.skills.SkillsDao;
import com.rds.observato.tasks.TaskView;
import com.rds.observato.tasks.TasksDao;
import com.rds.observato.users.LoginView;
import com.rds.observato.users.UserRoleView;
import com.rds.observato.users.UserView;
import com.rds.observato.users.UsersDao;
import com.rds.observato.validation.Validator;
import org.jdbi.v3.core.Jdbi;
import org.jdbi.v3.core.mapper.reflect.ConstructorMapper;
import org.jdbi.v3.guava.GuavaPlugin;
import org.jdbi.v3.jackson2.Jackson2Plugin;
import org.jdbi.v3.postgres.PostgresPlugin;
import org.jdbi.v3.sqlobject.SqlObjectPlugin;

public record ObservatoRepository(
    AccountsDao accounts,
    UsersDao users,
    ProjectsDao projects,
    TasksDao tasks,
    ResourcesDao resources,
    AssignmentDao assignments,
    SkillsDao skills)
    implements Repository {

  public ObservatoRepository {
    Validator.checkIsNull(users, "users");
    Validator.checkIsNull(tasks, "tasks");
    Validator.checkIsNull(accounts, "accounts");
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
        .registerRowMapper(ConstructorMapper.factory(SkillView.class))
        .registerRowMapper(ConstructorMapper.factory(TokenView.class))
        .registerRowMapper(ConstructorMapper.factory(ProjectView.class))
        .registerRowMapper(ConstructorMapper.factory(AccountView.class))
        .registerRowMapper(ConstructorMapper.factory(ResourceView.class))
        .registerRowMapper(ConstructorMapper.factory(UserRoleView.class))
        .registerRowMapper(ConstructorMapper.factory(AssignmentView.class))
        .registerRowMapper(ConstructorMapper.factory(UserAccountView.class));

    return new ObservatoRepository(
        jdbi.onDemand(AccountsDao.class),
        jdbi.onDemand(UsersDao.class),
        jdbi.onDemand(ProjectsDao.class),
        jdbi.onDemand(TasksDao.class),
        jdbi.onDemand(ResourcesDao.class),
        jdbi.onDemand(AssignmentDao.class),
        jdbi.onDemand(SkillsDao.class));
  }
}
