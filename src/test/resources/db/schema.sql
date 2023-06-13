DROP TABLE IF EXISTS ORDER_ITEM;
DROP TABLE IF EXISTS CART_ITEM;
DROP TABLE IF EXISTS PURCHASED_ITEM;
DROP TABLE IF EXISTS ORDERS;
DROP TABLE IF EXISTS USER_ROLES;
DROP TABLE IF EXISTS USERS;
DROP TABLE IF EXISTS GALLERY;
DROP TABLE IF EXISTS DOLL;

CREATE TABLE DOLL
(
    id          SERIAL PRIMARY KEY,
    name        VARCHAR UNIQUE          NOT NULL,
    description VARCHAR                 NOT NULL,
    price       NUMERIC(8, 2)           NOT NULL,
    quantity    INTEGER   DEFAULT 0     NOT NULL,
    poster      VARCHAR                 NOT NULL,
    created     TIMESTAMP DEFAULT now() NOT NULL
);

CREATE TABLE GALLERY
(
    doll_id  INTEGER,
    filename VARCHAR UNIQUE,
    PRIMARY KEY (doll_id, filename),
    FOREIGN KEY (doll_id) REFERENCES DOLL (id) ON DELETE CASCADE
);

CREATE TABLE USERS
(
    id           SERIAL PRIMARY KEY,
    name         VARCHAR                 NOT NULL,
    email        VARCHAR UNIQUE          NOT NULL,
    password     VARCHAR                 NOT NULL,
    lastname     VARCHAR,
    phone        VARCHAR UNIQUE,
    country      VARCHAR,
    city         VARCHAR,
    region       VARCHAR,
    street       VARCHAR,
    zipcode      VARCHAR,
    created      TIMESTAMP DEFAULT now() NOT NULL,
    enabled      BOOL      DEFAULT TRUE  NOT NULL,
    notice_email BOOL      DEFAULT FALSE NOT NULL,
    notice_phone BOOL      DEFAULT FALSE NOT NULL
);

CREATE TABLE CART_ITEM
(
    id       SERIAL PRIMARY KEY,
    doll_id  INTEGER           NOT NULL,
    user_id  INTEGER           NOT NULL,
    quantity INTEGER DEFAULT 1 NOT NULL,
    FOREIGN KEY (doll_id) REFERENCES DOLL (id) ON DELETE CASCADE,
    FOREIGN KEY (user_id) REFERENCES USERS (id) ON DELETE CASCADE,
    CONSTRAINT uk_user_doll UNIQUE (user_id, doll_id)
);

CREATE TABLE PURCHASED_ITEM
(
    id       SERIAL PRIMARY KEY,
    doll_id  INTEGER       NOT NULL,
    user_id  INTEGER       NOT NULL,
    quantity INTEGER       NOT NULL,
    price    NUMERIC(8, 2) NOT NULL,
    FOREIGN KEY (doll_id) REFERENCES DOLL (id),
    FOREIGN KEY (user_id) REFERENCES USERS (id) ON DELETE CASCADE
);

CREATE TABLE USER_ROLES
(
    user_id INTEGER NOT NULL,
    role    VARCHAR,
    CONSTRAINT uk_user_roles UNIQUE (user_id, role),
    FOREIGN KEY (user_id) REFERENCES USERS (id) ON DELETE CASCADE
);

CREATE TABLE ORDERS
(
    id              SERIAL PRIMARY KEY,
    user_id         INTEGER                 NOT NULL,
    status          VARCHAR                 NOT NULL,
    status_modified TIMESTAMP DEFAULT now() NOT NULL,
    total           NUMERIC(10, 2)          NOT NULL,
    delivery_date   DATE      DEFAULT now() + INTERVAL '4 day' NOT NULL,
    created         TIMESTAMP DEFAULT now() NOT NULL,
    FOREIGN KEY (user_id) REFERENCES USERS (id) ON DELETE CASCADE
);

CREATE TABLE ORDER_ITEM
(
    order_id INTEGER NOT NULL,
    item_id  INTEGER NOT NULL,
    PRIMARY KEY (order_id, item_id),
    FOREIGN KEY (order_id) REFERENCES ORDERS (id) ON DELETE CASCADE,
    FOREIGN KEY (item_id) REFERENCES PURCHASED_ITEM (id) ON DELETE CASCADE
);