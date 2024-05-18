CREATE TABLE users
(
    user_id    BIGSERIAL PRIMARY KEY,
    email      TEXT NOT NULL,
    "password" TEXT NOT NULL,
    nickName   TEXT NOT NULL,
    "role"     TEXT NOT NULL
);