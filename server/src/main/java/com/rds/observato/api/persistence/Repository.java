package com.rds.observato.api.persistence;

import com.rds.observato.accounts.AccountsDao;
import com.rds.observato.assignments.AssignmentDao;
import com.rds.observato.projects.ProjectsDao;
import com.rds.observato.resources.ResourcesDao;
import com.rds.observato.tasks.TasksDao;
import com.rds.observato.users.UsersDao;

public interface Repository {

  AccountsDao accounts();

  UsersDao users();

  ProjectsDao projects();

  TasksDao tasks();

  AssignmentDao assignments();

  ResourcesDao resources();
}
