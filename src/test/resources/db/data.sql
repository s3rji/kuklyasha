DELETE FROM DOLL;
DELETE FROM USERS;

INSERT INTO DOLL (name, description, price, image)
VALUES ('Doll1', 'Pretty Doll', 100.00, '/image1'),
       ('Doll2', 'Pretty Doll', 100.00, '/image2'),
       ('Doll3', 'Pretty Doll', 100.00, '/image3'),
       ('Doll4', 'Pretty Doll', 100.00, '/image4'),
       ('Doll5', 'Pretty Doll', 100.00, '/image5');

INSERT INTO USERS (name, email, password)
VALUES ('User', 'user@yandex.ru', 'password'),
       ('Admin', 'admin@gmail.com', 'admin');

INSERT INTO USER_ROLES (role, user_id)
VALUES ('USER', 1),
       ('ADMIN', 2),
       ('USER', 2);