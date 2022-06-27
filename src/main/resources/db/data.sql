DELETE FROM DOLL;
DELETE FROM USERS;

INSERT INTO DOLL (name, description, price, image)
VALUES ('Manyasha', 'Pretty Doll', 100.00, 'doll-1.JPG'),
       ('Paulina', 'Pretty Doll', 100.00, 'doll-2.JPG'),
       ('Angelica', 'Pretty Doll', 100.00, 'doll-3.JPG'),
       ('Olechka', 'Pretty Doll', 100.00, 'doll-4.JPG'),
       ('Peppy', 'Pretty Doll', 100.00, 'doll-5.JPG'),
       ('Polina', 'Pretty Doll', 100.00, 'doll-6.JPG'),
       ('Margo', 'Pretty Doll', 100.00, 'doll-7.JPG'),
       ('Marina', 'Pretty Doll', 100.00, 'doll-8.JPG');

INSERT INTO USERS (name, email, password)
VALUES ('User', 'user@yandex.ru', '{noop}password'),
       ('Admin', 'admin@gmail.com', '{noop}admin');

INSERT INTO USER_ROLES (role, user_id)
VALUES ('USER', 1),
       ('ADMIN', 2),
       ('USER', 2);