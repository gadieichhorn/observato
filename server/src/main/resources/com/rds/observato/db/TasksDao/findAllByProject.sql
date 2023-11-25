select t.id, t.revision, t.account_id, t.name, t.description, t.skills
from obs.project_tasks pt
         join obs.tasks t on pt.task_id = t.id
where t.account_id = :account
  and pt.project_id = :project
