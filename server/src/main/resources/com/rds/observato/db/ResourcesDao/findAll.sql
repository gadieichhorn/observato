select id, revision, account_id, name, skills
from obs.resources
where account_id = :account
