package com.rds.observato.db;

import com.rds.observato.assignments.AssignmentDao;
import org.jdbi.v3.sqlobject.CreateSqlObject;

public interface Repository {

  @CreateSqlObject
  AccountsDao accounts();

  @CreateSqlObject
  UsersDao users();

  @CreateSqlObject
  ProjectsDao projects();

  @CreateSqlObject
  TasksDao tasks();

  @CreateSqlObject
  AssignmentDao assignments();

  @CreateSqlObject
  ResourcesDao resources();

  @CreateSqlObject
  SkillsDao skills();
}
