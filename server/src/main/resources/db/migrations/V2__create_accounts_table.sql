CREATE TABLE IF NOT EXISTS obs.accounts
(
    id    BIGSERIAL PRIMARY KEY,
    name  TEXT NOT NULL,
    owner TEXT NOT NULL,
    UNIQUE (name)
);

CREATE TABLE IF NOT EXISTS obs.account_users
(
    user_id    BIGINT NOT NULL REFERENCES obs.users (id) ,
    account_id BIGINT NOT NULL REFERENCES obs.accounts (id) ,
    role       TEXT   NOT NULL,
    UNIQUE (user_id, account_id, role)
);
