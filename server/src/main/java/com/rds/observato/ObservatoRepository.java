package com.rds.observato;

import com.rds.observato.accounts.AccountRecord;
import com.rds.observato.accounts.TokenView;
import com.rds.observato.accounts.UserAccountView;
import com.rds.observato.assignments.AssignmentView;
import com.rds.observato.db.*;
import com.rds.observato.db.AssignmentDao;
import com.rds.observato.projects.ProjectRecord;
import com.rds.observato.resources.ResourceRecord;
import com.rds.observato.skills.SkillView;
import com.rds.observato.tasks.TaskRecord;
import com.rds.observato.users.LoginView;
import com.rds.observato.users.UserRoleView;
import com.rds.observato.users.UserRecord;
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
        .registerRowMapper(ConstructorMapper.factory(TaskRecord.class))
        .registerRowMapper(ConstructorMapper.factory(UserRecord.class))
        .registerRowMapper(ConstructorMapper.factory(LoginView.class))
        .registerRowMapper(ConstructorMapper.factory(SkillView.class))
        .registerRowMapper(ConstructorMapper.factory(TokenView.class))
        .registerRowMapper(ConstructorMapper.factory(ProjectRecord.class))
        .registerRowMapper(ConstructorMapper.factory(AccountRecord.class))
        .registerRowMapper(ConstructorMapper.factory(ResourceRecord.class))
        .registerRowMapper(ConstructorMapper.factory(UserRoleView.class))
        .registerRowMapper(ConstructorMapper.factory(AssignmentView.class))
        .registerRowMapper(ConstructorMapper.factory(UserAccountView.class));

    return jdbi.onDemand(Repository.class);
    //        jdbi.onDemand(UsersDao.class),
    //        jdbi.onDemand(ProjectsDao.class),
    //        jdbi.onDemand(TasksDao.class),
    //        jdbi.onDemand(ResourcesDao.class),
    //        jdbi.onDemand(AssignmentDao.class),
    //        jdbi.onDemand(SkillsDao.class));
  }
}
