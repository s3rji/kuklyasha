DELETE FROM ORDER_DOLL;
DELETE FROM DOLL;
DELETE FROM USERS;
DELETE FROM ORDERS;

INSERT INTO DOLL (name, description, price, quantity, image)
VALUES ('Doll1', 'Pretty Doll', 100.00, 1, '/image1'),
       ('Doll2', 'Pretty Doll', 100.00, 1, '/image2'),
       ('Doll3', 'Pretty Doll', 100.00, 1, '/image3'),
       ('Doll4', 'Pretty Doll', 100.00, 1, '/image4'),
       ('Doll5', 'Pretty Doll', 100.00, 1, '/image5');

INSERT INTO USERS (name, email, password, lastname, phone, country, city, region, street, zipcode)
VALUES ('User', 'user@yandex.ru', '{bcrypt}$2a$10$zAJ7/voOp9SwObfHN4CBLe/9KDnx.b/0TRcU53NzmmxuErVP15rWu', 'Pupkin',
        '79201112233', 'Россия', 'Москва', 'Москва', 'главная д.5', '123456'),
       ('Admin', 'admin@gmail.com', 'admin', 'Sidorov', '79251112233', 'Россия', 'Одинцово', 'Московская обл.',
        'вторая д.10', '123456');

INSERT INTO USER_ROLES (role, user_id)
VALUES ('USER', 1),
       ('ADMIN', 2),
       ('USER', 2);

INSERT INTO ORDERS (user_id, status, status_modified, total)
VALUES (1, 'NEW', '31.10.2022', 500.00),
       (1, 'DONE', '31.10.2022', 100.00);

INSERT INTO ORDER_DOLL (order_id, doll_id)
VALUES (1, 1),
       (1, 2),
       (1, 3),
       (1, 4),
       (1, 5),
       (2, 1);