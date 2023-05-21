DELETE FROM ORDER_ITEM;
DELETE FROM CART_ITEM;
DELETE FROM PURCHASED_ITEM;
DELETE FROM ORDERS;
DELETE FROM USER_ROLES;
DELETE FROM USERS;
DELETE FROM GALLERY;
DELETE FROM DOLL;

INSERT INTO DOLL (name, description, price, quantity, poster)
VALUES ('Doll1', 'Pretty Doll', 100.00, 2, '/image1'),
       ('Doll2', 'Pretty Doll', 100.00, 1, '/image2'),
       ('Doll3', 'Pretty Doll', 100.00, 1, '/image3'),
       ('Doll4', 'Pretty Doll', 100.00, 1, '/image4'),
       ('Doll5', 'Pretty Doll', 100.00, 1, '/image5');

INSERT INTO GALLERY (doll_id, filename)
VALUES (1, '/image1'),
       (1, '/image2'),
       (1, '/image3'),
       (1, '/image4');

INSERT INTO USERS (name, email, password, lastname, phone, country, city, region, street, zipcode, notice_email, notice_phone)
VALUES ('User', 'user@yandex.ru', '{bcrypt}$2a$10$zAJ7/voOp9SwObfHN4CBLe/9KDnx.b/0TRcU53NzmmxuErVP15rWu', 'Pupkin',
        '79201112233', 'Россия', 'Москва', 'Москва', 'главная д.5', '123456', true, true),
       ('Admin', 'admin@gmail.com', 'admin', 'Sidorov', '79251112233', 'Россия', 'Одинцово', 'Московская обл.',
        'вторая д.10', '123456', true, true);

INSERT INTO USER_ROLES (role, user_id)
VALUES ('USER', 1),
       ('ADMIN', 2),
       ('USER', 2);

INSERT INTO ORDERS (user_id, status, status_modified, total)
VALUES (1, 'NEW', '31.10.2022', 200.00),
       (1, 'DONE', '31.10.2022', 100.00);

INSERT INTO CART_ITEM (doll_id, user_id, quantity)
VALUES (1, 1, 1),
       (2, 1, 1),
       (3, 1, 2);

INSERT INTO PURCHASED_ITEM (doll_id, user_id, quantity, price)
VALUES (1, 1, 1, 100.00),
       (2, 1, 1, 100.00),
       (3, 1, 2, 100.00);

INSERT INTO ORDER_ITEM (order_id, item_id)
VALUES (1, 1),
       (1, 2),
       (2, 3);