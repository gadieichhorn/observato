CREATE SCHEMA IF NOT EXISTS users;

CREATE TABLE IF NOT EXISTS users.users
(
    id     SERIAL PRIMARY KEY,
    name   TEXT  NOT NULL,
    salt   BYTEA NOT NULL,
    secret BYTEA NOT NULL,
    UNIQUE (name)
);
