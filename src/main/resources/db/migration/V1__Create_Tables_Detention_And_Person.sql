CREATE TABLE IF NOT EXISTS persons
(
    id                BIGSERIAL PRIMARY KEY,
    first_name        VARCHAR(100) NOT NULL,
    last_name         VARCHAR(100) NOT NULL,
    email             VARCHAR(100) NOT NULL UNIQUE,
    birthday          DATE,
    birthplace        VARCHAR(250),
    ident_doc_type    INT          NOT NULL,
    doc_number_series VARCHAR(200) NOT NULL,
    issue_date        DATE
);

CREATE TABLE IF NOT EXISTS detentions
(
    id          SERIAL PRIMARY KEY,
    organ_code  INT           NOT NULL,
    doc_date    DATE          NOT NULL,
    doc_num     VARCHAR(30)   NOT NULL UNIQUE,
    purpose     VARCHAR(1000) NOT NULL,
    amount      BIGINT        NOT NULL,
    status_type INT           NOT NULL,
    person      INT REFERENCES persons (id)
);

CREATE UNIQUE INDEX IF NOT EXISTS ui_doc_num ON detentions (doc_num);

CREATE UNIQUE INDEX IF NOT EXISTS client_uniqueness ON persons (first_name, last_name, ident_doc_type, doc_number_series);









