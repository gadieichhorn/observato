select id, revision, account_id, name, description, skills
from obs.tasks
where account_id = :account
  and id = :id
