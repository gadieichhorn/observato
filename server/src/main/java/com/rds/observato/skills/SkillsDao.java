package com.rds.observato.skills;

import java.util.Set;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.statement.GetGeneratedKeys;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;

public interface SkillsDao {

  @SqlUpdate(
      """
                    insert into
                    obs.skills (account_id, revision, name, description)
                    values(:account,0, :name, :description)
                    """)
  @GetGeneratedKeys
  long create(
      @Bind("account") long account,
      @Bind("name") String name,
      @Bind("description") String description);

  @SqlQuery(
      """
      select id, revision, account_id , name , description
      from obs.skills
      where account_id = :account
      """)
  Set<SkillView> findAll(@Bind("account") long account);
}
