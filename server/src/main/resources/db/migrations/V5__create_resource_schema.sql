CREATE SCHEMA IF NOT EXISTS resources;

CREATE TABLE IF NOT EXISTS resources.resources
(
    id          SERIAL PRIMARY KEY,
    account_id  BIGINT NOT NULL,
    key         TEXT   NOT NULL,
    name        TEXT   NOT NULL,
    description TEXT   NOT NULL,
    UNIQUE (account_id, key),
    CONSTRAINT fk_account
        FOREIGN KEY (account_id)
            REFERENCES accounts.accounts (id)
);
