DROP TABLE IF EXISTS doll;

CREATE TABLE DOLL
(
    id          SERIAL PRIMARY KEY,
    name        VARCHAR UNIQUE          NOT NULL,
    description VARCHAR                 NOT NULL,
    price       NUMERIC(8, 2)           NOT NULL,
    image       VARCHAR                 NOT NULL,
    created     TIMESTAMP DEFAULT now() NOT NULL
);