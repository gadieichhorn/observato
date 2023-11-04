CREATE TABLE IF NOT EXISTS accounts.accounts
(
    id    SERIAL PRIMARY KEY,
    name  TEXT NOT NULL,
    owner TEXT NOT NULL,
    UNIQUE (name)
);
