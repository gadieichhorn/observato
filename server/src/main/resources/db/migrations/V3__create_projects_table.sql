CREATE SCHEMA IF NOT EXISTS projects;

CREATE TABLE IF NOT EXISTS projects.projects
(
    id          SERIAL PRIMARY KEY,
    account_id  BIGINT NOT NULL,
    name        TEXT   NOT NULL,
    description TEXT   NOT NULL,
    UNIQUE (account_id, name),
    CONSTRAINT fk_account
        FOREIGN KEY (account_id)
            REFERENCES accounts.accounts (id)
);
