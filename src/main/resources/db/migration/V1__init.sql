DROP TABLE IF EXISTS categories;
CREATE TABLE categories
(
    id   SERIAL,
    name VARCHAR(50) NOT NULL,
    PRIMARY KEY (id)
);

DROP TABLE IF EXISTS products;
CREATE TABLE products
(
    id          BIGSERIAL PRIMARY KEY,
    title       VARCHAR(128),
    price       NUMERIC(8, 2),
    category_id INT,
    created_at  TIMESTAMP,
    updated_at  TIMESTAMP,
    FOREIGN KEY (category_id)
        REFERENCES categories (id)
);

DROP TABLE IF EXISTS order_items;
CREATE TABLE order_items
(
    id             BIGSERIAL PRIMARY KEY,
    product_id     BIGINT,
    title          VARCHAR(255),

    price_per_item int,
    price          int,
    FOREIGN KEY (product_id)
        REFERENCES products (id)
);

DROP TABLE IF EXISTS status;
CREATE TABLE status
(
    id         SERIAL,
    updated_at TIMESTAMP,
    title      VARCHAR(50) NOT NULL,
    PRIMARY KEY (id)
);

DROP TABLE IF EXISTS users;
CREATE TABLE users
(
    id         bigserial primary key,
    phone      VARCHAR(15) NOT NULL UNIQUE,
    password   VARCHAR(50),
    email      VARCHAR(30) UNIQUE,
    first_name VARCHAR(50),
    last_name  VARCHAR(50),
    order_id   BIGINT,
    enabled    BOOLEAN,
    created_at TIMESTAMP,
    updated_at TIMESTAMP
--     FOREIGN KEY (order_id) добавляются после создания таблицы orders
--         REFERENCES orders (id)


);

DROP TABLE IF EXISTS authorities;
CREATE TABLE authorities
(
    userId    bigint      NOT NULL,
    authority varchar(50) NOT NULL,
    FOREIGN KEY (userId)
        REFERENCES users (id)
);

DROP TABLE IF EXISTS orders;
CREATE TABLE orders
(
    id            BIGSERIAL PRIMARY KEY,
    user_id       BIGINT,
    order_item_id BIGINT,
    total_price   NUMERIC(8, 2),
    phone         VARCHAR(15),
    address       VARCHAR(255),
    status_id     INTEGER,
    created_at    TIMESTAMP,
    updated_at    TIMESTAMP,
    FOREIGN KEY (user_id)
        REFERENCES users (id),
    FOREIGN KEY (order_item_id)
        REFERENCES order_items (id),
    FOREIGN KEY (status_id)
        REFERENCES status (id)
);

ALTER TABLE users
    ADD
        FOREIGN KEY (order_id)
            REFERENCES orders (id)
;

DROP TABLE IF EXISTS roles;
CREATE TABLE roles
(
    id   SERIAL,
    name VARCHAR(50) NOT NULL,
    PRIMARY KEY (id)
);

DROP TABLE IF EXISTS users_roles;
CREATE TABLE users_roles
(
    user_id INT NOT NULL,
    role_id INT NOT NULL,
    PRIMARY KEY (user_id, role_id),
    FOREIGN KEY (user_id)
        REFERENCES users (id),
    FOREIGN KEY (role_id)
        REFERENCES roles (id)
);

DROP TABLE IF EXISTS storage;
CREATE TABLE storage
(
    product_id         BIGSERIAL NOT NULL UNIQUE,
    quantity           INT,
    available_quantity INT,
    date_of_delivery   TIMESTAMP,
    updated_at         TIMESTAMP,
    FOREIGN KEY (product_id) REFERENCES products (id)
);



