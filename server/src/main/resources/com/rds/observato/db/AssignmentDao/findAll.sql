select id, revision, account_id, task_id, resource_id, start_time, end_time
from obs.assignments
where account_id = :account
