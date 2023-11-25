insert into obs.tokens
    (user_id, account_id, token, created_on)
values (:user, :account, :token, now())
