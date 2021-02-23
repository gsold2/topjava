DELETE FROM meals;
DELETE FROM user_roles;
DELETE FROM users;
ALTER SEQUENCE global_seq RESTART WITH 100000;

INSERT INTO users (name, email, password)
VALUES ('User', 'user@yandex.ru', 'password'),
       ('Admin', 'admin@gmail.com', 'admin');

INSERT INTO user_roles (role, user_id)
VALUES ('USER', 100000),
       ('ADMIN', 100001);

INSERT INTO meals (user_id, datetime, description, calories)
VALUES (100000, '2015-01-19 09:00', 'User_1_1', 510),
       (100000, '2015-01-19 13:00', 'User_1_2', 1000),
       (100000, '2015-01-19 20:00', 'User_1_3', 1500),
       (100000, '2015-02-19 09:00', 'User_2_1', 500),
       (100000, '2015-02-19 13:00', 'User_2_2', 500),
       (100000, '2015-02-19 20:00', 'User_2_3', 500),
       (100001, '2015-01-19 09:30', 'ADMIN_1_1', 510),
       (100001, '2015-01-19 13:30', 'ADMIN_1_2', 1000),
       (100001, '2015-01-19 20:30', 'ADMIN_1_3', 1500),
       (100001, '2015-02-19 09:30', 'ADMIN_2_1', 500),
       (100001, '2015-02-19 13:30', 'ADMIN_2_2', 500),
       (100001, '2015-02-19 20:30', 'ADMIN_2_3', 500);
