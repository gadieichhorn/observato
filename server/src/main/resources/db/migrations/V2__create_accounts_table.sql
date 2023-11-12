-- CREATE SCHEMA IF NOT EXISTS acc;

CREATE TABLE IF NOT EXISTS accounts
(
    id    BIGSERIAL PRIMARY KEY,
    name  TEXT NOT NULL,
    owner TEXT NOT NULL,
    UNIQUE (name)
);

CREATE TABLE IF NOT EXISTS account_users
(
    user_id    BIGINT NOT NULL REFERENCES users (id) ,
    account_id BIGINT NOT NULL REFERENCES accounts (id) ,
    role       TEXT   NOT NULL,
    UNIQUE (user_id, account_id, role)
);
