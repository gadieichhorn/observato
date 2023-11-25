select id, revision, account_id, name, description
from obs.skills
where account_id = :account
