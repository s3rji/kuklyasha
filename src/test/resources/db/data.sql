DELETE FROM DOLL;
DELETE FROM USERS;

INSERT INTO DOLL (name, description, price, image)
VALUES ('Doll1', 'Pretty Doll', 100.00, '/image1'),
       ('Doll2', 'Pretty Doll', 100.00, '/image2'),
       ('Doll3', 'Pretty Doll', 100.00, '/image3'),
       ('Doll4', 'Pretty Doll', 100.00, '/image4'),
       ('Doll5', 'Pretty Doll', 100.00, '/image5');

INSERT INTO USERS (name, email, password, lastname, phone, country, city, region, street, zipcode)
VALUES ('User', 'user@yandex.ru', '{bcrypt}$2a$10$zAJ7/voOp9SwObfHN4CBLe/9KDnx.b/0TRcU53NzmmxuErVP15rWu', 'Pupkin',
        '+79201112233', 'Россия', 'Москва', 'Москва', 'главная д.5', '123456'),
       ('Admin', 'admin@gmail.com', 'admin', 'Sidorov', '+79251112233', 'Россия', 'Одинцово', 'Московская обл.',
        'вторая д.10', '123456');

INSERT INTO USER_ROLES (role, user_id)
VALUES ('USER', 1),
       ('ADMIN', 2),
       ('USER', 2);