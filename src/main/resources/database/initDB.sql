CREATE TABLE IF NOT EXISTS persons
(
    id                SERIAL PRIMARY KEY,
    first_name        VARCHAR(100) NOT NULL,
    last_name         VARCHAR(100) NOT NULL,
    birthday          DATE,
    birthplace        VARCHAR(250),
    ident_doc_type    INT          NOT NULL,
    doc_number_series VARCHAR(200) NOT NULL,
    issue_date        DATE
);

CREATE UNIQUE INDEX IF NOT EXISTS client_uniqueness ON persons (first_name, last_name, ident_doc_type, doc_number_series);

CREATE TABLE IF NOT EXISTS arrests
(
    id             SERIAL PRIMARY KEY,
    organ_code     INT           NOT NULL,
    doc_date       DATE          NOT NULL,
    doc_num        VARCHAR(30)   NOT NULL,
    purpose        VARCHAR(1000) NOT NULL,
    amount         BIGINT        NOT NULL,
    status_type    VARCHAR(25)   NOT NULL,
    person         INT REFERENCES persons (id)
);

CREATE UNIQUE INDEX IF NOT EXISTS ui_doc_num ON arrests (doc_num);








