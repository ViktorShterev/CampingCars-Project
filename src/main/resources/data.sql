/*Populating tables*/

INSERT INTO categories (id, category)
VALUES (1, 'CAMPER'),
       (2, 'CARAVAN');

INSERT INTO roles (id, role)
VALUES (1, 'USER'),
       (2, 'ADMIN');

INSERT INTO models (name, brand_name, category_id)
VALUES ('CARAONE 390 PUH', 'Weinsberg', 2),
       ('540 KMFe De Luxe', 'Hobby', 2),
       ('Sassino 470K', 'LMC', 2),
       ('680 Selected BQD', 'Polar', 2),
       ('Lunar', 'ABI', 2),
       ('Tendenza 650 SFD', 'Fendt', 2),
       ('Exclusive 650', 'Knaus', 2),
       ('BULLET 31BHPR PREMIER', 'Keystone', 2),
       ('855F Exclusive', 'Rapido', 1),
       ('MATRIX M 670', 'Adria', 1),
       ('Travel Master', 'Kabe', 1),
       ('Aristeo', 'Benimar', 1),
       ('C-Tourer', 'Carthago', 1),
       ('Viseo 726 Integral', 'Buerstner', 1),
       ('EX 698', 'Hymer', 1),
       ('7150-2 Esprit', 'Dethleffs', 1);

INSERT INTO users (age, created, email, first_name, is_active, last_name, password, uuid)
VALUES (30, LOCALTIMESTAMP, 'admin@test.com', 'Admin', 1, 'Adminov',
        '$2a$10$.OvjWSc3DLHbrP57kf5j.O35EqFnV2IgxzGqDXpBWnm0rUhkLtfyC', UUID());

INSERT INTO users_roles (user_id, roles_id)
VALUES (1, 1),
       (1, 2);

INSERT INTO offers (model_id, price, engine, transmission, year, mileage, description, image_url, beds, horse_power,
                    modified, uuid, category_id, seller_id, created)
VALUES (10, 70000, 'DIESEL', 'AUTOMATIC', 2018, 35000,
        'I am selling a very good and reliable camper model Adria M 670. Тhe camper has a high level of equipment and many extras. It has 5 beds, enough for the whole family, as well as a large and comfortable bathroom. There is an awning which makes it ideal for holidays around the sea.',
        'https://www.potovalnik.si/images/najem-avtodoma/najem_avtodoma_naslovna.jpg',
        5, 160, LOCALTIMESTAMP, UUID(), 1, 1, LOCALTIMESTAMP),
       (2, 25000, NULL, NULL, 2016, NULL,
        'Sell caravan Hobby. Extremely well maintained caravan. Has 4 beds and spacious living room with kitchen. Has a new filter and pump in the bathroom. Its fully prepared for next owner and destination.',
        'https://cdn2.focus.bg/mobile/photosorg/033/9/big//91739435040890033_dz.webp',
        4, NULL, LOCALTIMESTAMP, UUID(), 2, 1, LOCALTIMESTAMP);

