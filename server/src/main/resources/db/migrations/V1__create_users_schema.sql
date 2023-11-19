-- CREATE SCHEMA IF NOT EXISTS usr;

CREATE TABLE IF NOT EXISTS obs.users
(
    id       BIGSERIAL PRIMARY KEY,
    revision INT DEFAULT 0,
    name     TEXT  NOT NULL,
    salt     BYTEA NOT NULL,
    secret   BYTEA NOT NULL,
    UNIQUE (name)
);
