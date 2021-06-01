insert into roles (name)
values ('ROLE_USER'),
       ('ROLE_ADMIN');

insert into users (id, username, password, email, enabled, cart_id)
values (1, 'bob1', '$2y$12$yr/ojWL6JQCpku.5vlcGuuIAkROTMOkIupVUzzRMqQvBqpUgn4xA2', 'bob1@gmail.com', true, 1);

insert into users (username, password, email, enabled)
values ('bob2', '$2y$12$yr/ojWL6JQCpku.5vlcGuuIAkROTMOkIupVUzzRMqQvBqpUgn4xA2', 'bob2@gmail.com', true);

insert into users_roles (user_id, role_id)
values (1, 1),
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


insert into order_items(id)
values (1);
--        (2, 2);

insert into delivery_details(id)
values (1);



insert into orders(id)
values (1);


UPDATE  delivery_details set order_id = 1 where id = 1;

--        (2, 1, 2);

-- insert into orders(id, user_id, order_item_id)
-- values (1, 1, 1),
--        (2, 1, 1);

UPDATE  users set order_id = 1 where username = 'bob1';




UPDATE orders set order_item_id = 1, address = 'some_address', created_at = now(), delivery_details_id = 1 where id = 1;

