DELETE FROM DOLL;
DELETE FROM USER_INFO;
DELETE FROM USERS;

INSERT INTO DOLL (name, description, price, image)
VALUES ('Doll1', 'Pretty Doll', 100.00, '/image1'),
       ('Doll2', 'Pretty Doll', 100.00, '/image2'),
       ('Doll3', 'Pretty Doll', 100.00, '/image3'),
       ('Doll4', 'Pretty Doll', 100.00, '/image4'),
       ('Doll5', 'Pretty Doll', 100.00, '/image5');

INSERT INTO USERS (name, email, password)
VALUES ('User', 'user@yandex.ru', '{bcrypt}$2a$10$zAJ7/voOp9SwObfHN4CBLe/9KDnx.b/0TRcU53NzmmxuErVP15rWu'),
       ('Admin', 'admin@gmail.com', 'admin');

INSERT INTO USER_ROLES (role, user_id)
VALUES ('USER', 1),
       ('ADMIN', 2),
       ('USER', 2);

INSERT INTO USER_INFO (user_id, lastname, phone, country, city, region, street, zipcode)
VALUES (1, 'Pupkin', '+79201112233', 'Россия', 'Москва', 'Москва', 'главная д.5', '123456'),
       (2, 'Sidorov', '+79251112233', 'Россия', 'Одинцово', 'Московская обл.', 'вторая д.10', '123456');