package com.rds.observato.api.persistence;

import com.rds.observato.persistence.accounts.AccountDao;
import com.rds.observato.persistence.assignments.AssignmentDao;
import com.rds.observato.persistence.projects.ProjectsDao;
import com.rds.observato.persistence.resources.ResourcesDao;
import com.rds.observato.persistence.tasks.TasksDao;
import com.rds.observato.persistence.users.UsersDao;

public interface Repository {

  AccountDao accounts();

  UsersDao users();

  ProjectsDao projects();

  TasksDao tasks();

  AssignmentDao assignments();

  ResourcesDao resources();
}
