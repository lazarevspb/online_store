insert into roles (name)
values
('ROLE_USER'),
('ROLE_ADMIN');

insert into users (username, password, email, enabled, cart_id)
values
('bob1', '$2y$12$yr/ojWL6JQCpku.5vlcGuuIAkROTMOkIupVUzzRMqQvBqpUgn4xA2', 'bob1@gmail.com', true, 1);

insert into users (username, password, email, enabled)
values
('bob2', '$2y$12$yr/ojWL6JQCpku.5vlcGuuIAkROTMOkIupVUzzRMqQvBqpUgn4xA2', 'bob2@gmail.com', true);

insert into users_roles (user_id, role_id)
values
(1, 1),
(2, 2);

insert into products (title, price, category_id)
values
('Banana', 50, 1),
('Bread', 40, 1),
('Meat', 500, 3);






