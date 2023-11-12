-- CREATE SCHEMA IF NOT EXISTS rsc;

CREATE TABLE IF NOT EXISTS resources
(
    id          BIGSERIAL PRIMARY KEY,
    account_id  BIGINT NOT NULL REFERENCES accounts (id),
    name        TEXT   NOT NULL,
    description TEXT   NOT NULL,
    UNIQUE (account_id, name)
);
