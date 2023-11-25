package com.rds.observato;

import com.rds.observato.assignments.AssignmentDao;
import com.rds.observato.db.AccountsDao;
import com.rds.observato.db.ProjectsDao;
import com.rds.observato.db.ResourcesDao;
import com.rds.observato.db.SkillsDao;
import com.rds.observato.db.TasksDao;
import com.rds.observato.users.UsersDao;

public interface Repository {

  AccountsDao accounts();

  UsersDao users();

  ProjectsDao projects();

  TasksDao tasks();

  AssignmentDao assignments();

  ResourcesDao resources();

  SkillsDao skills();
}
