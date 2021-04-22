insert into roles (name)
values
('ROLE_USER'),
('ROLE_ADMIN');

insert into users (username, password, email, enabled)
values
('bob1', '$2y$12$yr/ojWL6JQCpku.5vlcGuuIAkROTMOkIupVUzzRMqQvBqpUgn4xA2', 'bob1@gmail.com', true),
('bob2', '$2y$12$yr/ojWL6JQCpku.5vlcGuuIAkROTMOkIupVUzzRMqQvBqpUgn4xA2', 'bob2@gmail.com', true);

insert into users_roles (user_id, role_id)
values
(1, 1),
(2, 2);

insert into products (title, price)
values
('Banana', 50),
('Bread', 40),
('Meat', 500);


insert into categories
(title, description) values
('food', 'some food products'),
('fruits', 'some fruit'),
('vegetables', 'some vegetables'),
('electronics', 'some electronics'),
('non-food', 'all non-food products');