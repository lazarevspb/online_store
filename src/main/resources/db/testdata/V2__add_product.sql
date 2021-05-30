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



insert into categories
(title, description)
values ('food', 'some food products'),
       ('fruits', 'some fruit'),
       ('vegetables', 'some vegetables'),
       ('electronics', 'some electronics'),
       ('non-food', 'all non-food products');


insert into products(title, price, category_id)
values ('Product_1', 10.00, 1),
       ('Product_2', 20.00, 3),
       ('Product_3', 30.00, 3),
       ('Product_4', 40.00, 4)
--        ,('Product_5', 50.00, 5)
;


