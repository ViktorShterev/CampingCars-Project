/*Populating models table*/

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

INSERT INTO offers (id, beds, created, description, engine, horse_power, image_url, mileage, modified, price,
                    transmission, uuid, year, category_id, model_id, seller_id)
VALUES (1, 4, LOCALTIMESTAMP,
        'For sale is a very good 2022 Adria Matrix motorhome. It has 4 comfortable beds for sleeping, a wide bathroom with toilet, and a very economical diesel engine of 140 horsepower.',
        'DIESEL', 140, 'https://www.potovalnik.si/images/najem-avtodoma/najem_avtodoma_naslovna.jpg',
        15000, NULL, 110000, 'AUTOMATIC', UUID(), 2022, 1, 10, 1),
       (2, 4, LOCALTIMESTAMP,
        'For sale is a preserved caravan ABI. With 4 spacious beds for sleeping and own bathroom. Very well maintained and cleaned, ideal for family holidays by the sea.',
        NULL, NULL,
        'https://www.qualitycaravans.com/images/stories/virtuemart/product/resized/used-touring-caravan-for-sale-2013-lunar-lexon-540-torksey-caravans-(1)_1000x750.jpg',
        NULL, NULL, 25000, NULL, UUID(), 2016, 2, 5, 1),
       (3, 5, LOCALTIMESTAMP,
        'Camper Benimar Aristeo, with 5 sleeping places and wonderful comfortable equipment. New gas hob and water filter. Decently maintained with many more miles to go.',
        'PETROL', 160, 'https://www.comercialcaravaning.com/wp-content/uploads/2020/12/comercial-caravaning-1-31.jpg',
        80000, NULL, 50000, 'MANUAL', UUID(), 2014, 1, 12, 1),
       (4, 5, LOCALTIMESTAMP,
        'I am selling my faithfully serving Hobby caravan manufactured 2010. Capable of sleeping 5 people, with comfortable kitchenette and outdoor tent.',
        NULL, NULL, 'https://i.ytimg.com/vi/JXqTRvvRm1o/maxresdefault.jpg', NULL, NULL, 13000, NULL, UUID(), 2010, 2, 2,
        1),
       (5, 4, LOCALTIMESTAMP,
        'For sale Dethleffs Esprit 7150 camper from 2008. Extremely reliable machine, never left us on the road. Highly equipped for its years and lots of extras. Has been serviced regularly and new gas nozzles and bath pumps have been purchased, battery is also new.',
        'DIESEL', 120, 'https://cdn.truckscout24.com/data/listing/img/vga/ts/46/54/18653621-01.jpg?v=1741234342',
        135000, NULL, 23000, 'AUTOMATIC', UUID(), 2008, 1, 16, 1);
