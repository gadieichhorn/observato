CREATE SCHEMA IF NOT EXISTS accounts;

CREATE TABLE IF NOT EXISTS accounts.accounts
(
    id    SERIAL PRIMARY KEY,
    name  TEXT NOT NULL,
    owner TEXT NOT NULL,
    UNIQUE (name)
);

CREATE TABLE IF NOT EXISTS accounts.users
(
    user_id    BIGINT NOT NULL,
    account_id BIGINT NOT NULL,
    role       TEXT   NOT NULL,
    UNIQUE (user_id, account_id),
    CONSTRAINT fk_account
        FOREIGN KEY (account_id)
            REFERENCES accounts.accounts (id),
    CONSTRAINT fk_task
        FOREIGN KEY (user_id)
            REFERENCES users.users (id)
);
