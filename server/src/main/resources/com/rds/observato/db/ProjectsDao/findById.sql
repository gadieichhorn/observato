select id, revision, account_id, name, description
from obs.projects
where account_id = :account
  and id = :id
