package com.rds.observato.api.persistence;

import com.rds.observato.persistence.accounts.AccountDao;
import com.rds.observato.persistence.projects.ProjectsDao;
import com.rds.observato.persistence.users.UsersDao;

public interface Repository {

  AccountDao accounts();

  UsersDao users();

  ProjectsDao projects();
}
