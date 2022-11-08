DELETE FROM ORDER_DOLL;
DELETE FROM DOLL;
DELETE FROM USERS;
DELETE FROM ORDERS;

INSERT INTO DOLL (name, description, price, image)
VALUES ('Manyasha', 'Встречайте! Декоративная фарфоровая кукла Плюш&К ручной работы идеально подойдет для интерьера и в качестве подарка! Подарочная кукла изготовлена из экологически чистого фарфора с росписью, а ткани одежды всех кукол, изготовлены из шелка, хлопка, капрона и расшиты вручную. Декоративные интерьерные куклы имеют красивую и прочную упаковку в виде коробки. Ограниченная серия коллекционных кукол ручной работы от марки Плюш&К совместно с Bene! Винтажные и викторианские куклы выполнены в различных цветах! ', 100.00, 'doll-1.JPG'),
       ('Paulina', 'Встречайте! Декоративная фарфоровая кукла Плюш&К ручной работы идеально подойдет для интерьера и в качестве подарка! Подарочная кукла изготовлена из экологически чистого фарфора с росписью, а ткани одежды всех кукол, изготовлены из шелка, хлопка, капрона и расшиты вручную. Декоративные интерьерные куклы имеют красивую и прочную упаковку в виде коробки. Ограниченная серия коллекционных кукол ручной работы от марки Плюш&К совместно с Bene! Винтажные и викторианские куклы выполнены в различных цветах! ', 100.00, 'doll-2.JPG'),
       ('Angelica', 'Встречайте! Декоративная фарфоровая кукла Плюш&К ручной работы идеально подойдет для интерьера и в качестве подарка! Подарочная кукла изготовлена из экологически чистого фарфора с росписью, а ткани одежды всех кукол, изготовлены из шелка, хлопка, капрона и расшиты вручную. Декоративные интерьерные куклы имеют красивую и прочную упаковку в виде коробки. Ограниченная серия коллекционных кукол ручной работы от марки Плюш&К совместно с Bene! Винтажные и викторианские куклы выполнены в различных цветах! ', 100.00, 'doll-3.JPG'),
       ('Olechka', 'Встречайте! Декоративная фарфоровая кукла Плюш&К ручной работы идеально подойдет для интерьера и в качестве подарка! Подарочная кукла изготовлена из экологически чистого фарфора с росписью, а ткани одежды всех кукол, изготовлены из шелка, хлопка, капрона и расшиты вручную. Декоративные интерьерные куклы имеют красивую и прочную упаковку в виде коробки. Ограниченная серия коллекционных кукол ручной работы от марки Плюш&К совместно с Bene! Винтажные и викторианские куклы выполнены в различных цветах! ', 100.00, 'doll-4.JPG'),
       ('Peppy', 'Встречайте! Декоративная фарфоровая кукла Плюш&К ручной работы идеально подойдет для интерьера и в качестве подарка! Подарочная кукла изготовлена из экологически чистого фарфора с росписью, а ткани одежды всех кукол, изготовлены из шелка, хлопка, капрона и расшиты вручную. Декоративные интерьерные куклы имеют красивую и прочную упаковку в виде коробки. Ограниченная серия коллекционных кукол ручной работы от марки Плюш&К совместно с Bene! Винтажные и викторианские куклы выполнены в различных цветах! ', 100.00, 'doll-5.JPG'),
       ('Polina', 'Встречайте! Декоративная фарфоровая кукла Плюш&К ручной работы идеально подойдет для интерьера и в качестве подарка! Подарочная кукла изготовлена из экологически чистого фарфора с росписью, а ткани одежды всех кукол, изготовлены из шелка, хлопка, капрона и расшиты вручную. Декоративные интерьерные куклы имеют красивую и прочную упаковку в виде коробки. Ограниченная серия коллекционных кукол ручной работы от марки Плюш&К совместно с Bene! Винтажные и викторианские куклы выполнены в различных цветах! ', 100.00, 'doll-6.JPG'),
       ('Margo', 'Встречайте! Декоративная фарфоровая кукла Плюш&К ручной работы идеально подойдет для интерьера и в качестве подарка! Подарочная кукла изготовлена из экологически чистого фарфора с росписью, а ткани одежды всех кукол, изготовлены из шелка, хлопка, капрона и расшиты вручную. Декоративные интерьерные куклы имеют красивую и прочную упаковку в виде коробки. Ограниченная серия коллекционных кукол ручной работы от марки Плюш&К совместно с Bene! Винтажные и викторианские куклы выполнены в различных цветах! ', 100.00, 'doll-7.JPG'),
       ('Marina', 'Встречайте! Декоративная фарфоровая кукла Плюш&К ручной работы идеально подойдет для интерьера и в качестве подарка! Подарочная кукла изготовлена из экологически чистого фарфора с росписью, а ткани одежды всех кукол, изготовлены из шелка, хлопка, капрона и расшиты вручную. Декоративные интерьерные куклы имеют красивую и прочную упаковку в виде коробки. Ограниченная серия коллекционных кукол ручной работы от марки Плюш&К совместно с Bene! Винтажные и викторианские куклы выполнены в различных цветах! ', 100.00, 'doll-8.JPG');

INSERT INTO USERS (name, email, password, lastname, phone, country, city, region, street, zipcode)
VALUES ('User', 'user@yandex.ru', '{noop}password', 'Pupkin', '79201112233', 'Россия', 'Москва', 'Москва', 'главная д.5', '123456'),
       ('Admin', 'admin@gmail.com', '{noop}admin', 'Sidorov', '79251112233', 'Россия', 'Одинцово', 'Московская обл.', 'вторая д.10', '123456');

INSERT INTO USER_ROLES (role, user_id)
VALUES ('USER', 1),
       ('ADMIN', 2),
       ('USER', 2);

INSERT INTO ORDERS (user_id, status, status_modified, total)
VALUES (1, 'NEW', '01.11.2022', 500.00),
       (1, 'DONE', '31.10.2022', 1000.00),
       (2, 'DELIVERY', '01.11.2022', 2000.00);

INSERT INTO ORDER_DOLL (order_id, doll_id)
VALUES (1, 1),
       (1, 2),
       (2, 3),
       (3, 4),
       (3, 5);