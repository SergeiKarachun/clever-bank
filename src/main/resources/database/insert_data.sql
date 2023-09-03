INSERT INTO currencies (id, name)
VALUES (1, 'Dollar USA'),
       (2, 'Euro'),
       (3, 'Ruble BLR');
SELECT SETVAL('currencies_id_seq', (SELECT MAX(id) FROM currencies));

