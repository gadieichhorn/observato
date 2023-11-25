update obs.tasks
set name= :name,
    description = :description,
    revision = revision + 1
where account_id = :account
  and id = :id
  and revision = :revision;
