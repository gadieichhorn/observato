select user_id, account_id, role
from obs.account_users
where user_id = :user
  and account_id = :account
