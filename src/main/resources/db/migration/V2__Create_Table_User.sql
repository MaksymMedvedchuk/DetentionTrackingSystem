CREATE TABLE IF NOT EXISTS users
(
    id           BIGSERIAL PRIMARY KEY,
    first_name   VARCHAR(100),
    last_name    VARCHAR(100),
    email        VARCHAR(255) NOT NULL UNIQUE,
    is_verified  BOOLEAN,
    role         VARCHAR(20)  NOT NULL,
    password     VARCHAR(255)
);
