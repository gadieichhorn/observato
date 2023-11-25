select t.user_id, t.account_id, array_agg(au.role) as roles, u.name
from obs.tokens t
         join obs.users u on u.id = t.user_id
         join obs.account_users au on t.account_id = au.account_id
where token = :token
  and t.user_id = au.user_id
  and t.account_id = au.account_id
group by t.user_id, t.account_id, u.name
