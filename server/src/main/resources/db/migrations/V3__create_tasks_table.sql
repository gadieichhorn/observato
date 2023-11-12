CREATE SCHEMA IF NOT EXISTS tasks;

CREATE TABLE IF NOT EXISTS tasks.tasks
(
    id         SERIAL PRIMARY KEY,
    account_id BIGINT NOT NULL,
    name       TEXT   NOT NULL,
    UNIQUE (account_id, name),
    CONSTRAINT fk_account
        FOREIGN KEY (account_id)
            REFERENCES accounts.accounts (id)
);
