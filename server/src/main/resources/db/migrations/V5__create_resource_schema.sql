CREATE TABLE IF NOT EXISTS obs.resources
(
    id          BIGSERIAL PRIMARY KEY,
    account_id  BIGINT NOT NULL REFERENCES obs.accounts (id),
    name        TEXT   NOT NULL,
    UNIQUE (account_id, name)
);
