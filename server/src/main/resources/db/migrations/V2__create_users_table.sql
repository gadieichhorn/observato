CREATE TABLE IF NOT EXISTS users.users
(
    id   SERIAL PRIMARY KEY,
    name TEXT NOT NULL,
    UNIQUE (name)
);
