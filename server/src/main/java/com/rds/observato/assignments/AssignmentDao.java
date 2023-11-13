package com.rds.observato.assignments;

import java.util.Set;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.statement.SqlQuery;

public interface AssignmentDao {
  @SqlQuery(
      """
          select id,name,owner from obs.assignments where account_id = :account
          """)
  Set<AssignmentView> getAll(@Bind("account") long account);
}
