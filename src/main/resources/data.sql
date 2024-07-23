/*Populating brands table*/

INSERT INTO brands (name)
VALUES ('Weinsberg'),
       ('Hobby'),
       ('LMC'),
       ('Polar'),
       ('ABI'),
       ('Fendt'),
       ('Knaus'),
       ('Keystone'),
       ('Rapido'),
       ('Adria'),
       ('Kabe'),
       ('Benimar'),
       ('Carthago'),
       ('Bürstner'),
       ('Hymer'),
       ('Dethleffs');

/*Populating models table*/

INSERT INTO models (name, brand_id, category_id)
VALUES ('CARAONE 390 PUH', 1, 2),
       ('540 KMFe De Luxe', 2, 2),
       ('Sassino 470K', 3, 2),
       ('680 Selected BQD', 4, 2),
       ('Lunar', 5, 2),
       ('Tendenza 650 SFD', 6, 2),
       ('Exclusive 650', 7, 2),
       ('BULLET 31BHPR PREMIER', 8, 2),
       ('855F Exclusive', 9, 1),
       ('MATRIX M 670', 10, 1),
       ('Travel Master', 11, 1),
       ('Aristeo', 12, 1),
       ('C-Tourer', 13, 1),
       ('Viseo 726 Integral', 14, 1),
       ('EX 698', 15, 1),
       ('7150-2 Esprit', 16, 1);

