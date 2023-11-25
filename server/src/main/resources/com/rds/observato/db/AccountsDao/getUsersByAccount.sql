select user_id, account_id, role
from obs.account_users
where account_id = :account
