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
VALUES (100000, '2015-01-19 09:23:54', 'User_1 завтрак', 510),
       (100000, '2015-01-19 13:23:54', 'User_1 обед', 1000),
       (100000, '2015-01-19 10:23:54', 'User_1 ужин', 1500),
       (100000, '2015-02-19 09:23:54', 'User_2 завтрак', 500),
       (100000, '2015-02-19 13:23:54', 'User_2 обед', 500),
       (100000, '2015-02-19 10:23:54', 'User_2 ужин', 500),
       (100001, '2015-01-19 09:33:54', 'ADMIN_1 завтрак', 510),
       (100001, '2015-01-19 13:33:54', 'ADMIN_1 обед', 1000),
       (100001, '2015-01-19 10:33:54', 'ADMIN_1 ужин', 1500),
       (100001, '2015-02-19 09:33:54', 'ADMIN_2 завтрак', 500),
       (100001, '2015-02-19 13:33:54', 'ADMIN_2 обед', 500),
       (100001, '2015-02-19 10:33:54', 'ADMIN_2 ужин', 500);
