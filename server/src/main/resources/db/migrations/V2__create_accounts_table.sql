CREATE TABLE IF NOT EXISTS obs.accounts
(
    id       BIGSERIAL PRIMARY KEY,
    revision INT DEFAULT 0,
    name     TEXT   NOT NULL,
    owner_id BIGINT NOT NULL REFERENCES obs.users (id),
    UNIQUE (name)
);

CREATE TABLE IF NOT EXISTS obs.account_users
(
    user_id    BIGINT NOT NULL REFERENCES obs.users (id),
    account_id BIGINT NOT NULL REFERENCES obs.accounts (id),
    role       TEXT   NOT NULL,
    UNIQUE (user_id, account_id, role)
);

CREATE TABLE IF NOT EXISTS obs.tokens
(
    id          BIGSERIAL PRIMARY KEY,
    user_id     bigint    NOT NULL REFERENCES obs.users (id),
    account_id  BIGINT    NOT NULL REFERENCES obs.accounts (id),
    token       BYTEA     NOT NULL,
    created_on  TIMESTAMP NOT NULL,
    UNIQUE (user_id, account_id)
);
