CREATE TABLE IF NOT EXISTS tokens
(
    id              BIGSERIAL PRIMARY KEY,
    token_value     VARCHAR(1000),
    token_type      VARCHAR(50),
    expiration_date TIMESTAMP,
    user_id         BIGSERIAL,
        FOREIGN KEY (user_id) REFERENCES users (id)
);