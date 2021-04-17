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

/*Элементы заказа, из них будем формировать заказ или корзину
  Самой корзины нет, так как в базе храниться не будет*/
DROP TABLE IF EXISTS order_items;
CREATE TABLE order_items
(
    id             BIGSERIAL PRIMARY KEY,
    product_id     BIGINT,
    title          VARCHAR(255),
    price_per_item NUMERIC(8, 2),
    price          NUMERIC(8, 2),
    FOREIGN KEY (product_id)
        REFERENCES products (id)
);
/*Статус выполнения заказа, например: создан, оплачен, выполнен итд */
DROP TABLE IF EXISTS order_status;
CREATE TABLE order_status
(
    id         SERIAL,
    updated_at TIMESTAMP,
    title      VARCHAR(50) NOT NULL,
    PRIMARY KEY (id)
);

/*Пользователи*/
DROP TABLE IF EXISTS users;
CREATE TABLE users
(
    id         bigserial primary key,
    phone      VARCHAR(15) NOT NULL UNIQUE,
    password   VARCHAR(100),
    email      VARCHAR(30) UNIQUE,
    first_name VARCHAR(50),
    last_name  VARCHAR(50),
    order_id   BIGINT,
    enabled    BOOLEAN, /* пользователя можно будет деактивировать */
    created_at TIMESTAMP,
    updated_at TIMESTAMP
--     FOREIGN KEY (order_id) - добавляются после создания таблицы orders
--         REFERENCES orders (id)
);

/*Таблица хранит информации об акциях и скидках.
  Так же о товарах и категориях товаров на которые эти скидки распространяются*/
DROP TABLE IF EXISTS promotional_events;
CREATE TABLE promotional_events
(
    id              BIGSERIAL PRIMARY KEY,
    events_title    VARCHAR(255),
    actual          BOOLEAN,
    discount        NUMERIC(8, 2),
    product_id      BIGINT,
    category_id     INT,
    expiration_date TIMESTAMP,
    created_at      TIMESTAMP,
    updated_at      TIMESTAMP,
    FOREIGN KEY (product_id)
        REFERENCES products (id),
    FOREIGN KEY (category_id)
        REFERENCES categories (id)
);

/*статусы доставки*/
DROP TABLE IF EXISTS delivery_status;
CREATE TABLE delivery_status
(
    id    SERIAL,
    title VARCHAR(50) NOT NULL,
    PRIMARY KEY (id)
);

/* Детали доставки */
DROP TABLE IF EXISTS delivery_details;
CREATE TABLE delivery_details
(
    id                 BIGSERIAL   NOT NULL UNIQUE,
    order_id           BIGINT      NOT NULL UNIQUE,
    phone              VARCHAR(15) NOT NULL UNIQUE,
    address            VARCHAR(255),
    delivery_date      TIMESTAMP,
    price              NUMERIC(8, 2),
    status_delivery_id INTEGER,
    updated_at         TIMESTAMP,

--     FOREIGN KEY (order_id) REFERENCES orders (id),
    FOREIGN KEY (status_delivery_id) REFERENCES delivery_status (id),
    PRIMARY KEY (id)
);


/*В этой таблице будет храниться заказ, по ИД заказа можем получить юзера
  и статус самого заказа*/
DROP TABLE IF EXISTS orders;
CREATE TABLE orders
(
    id                   BIGSERIAL PRIMARY KEY,
    user_id              BIGINT,
    order_item_id        BIGINT,
    discount             NUMERIC(8, 2),
    promotional_event_id BIGINT,
    total_discount       NUMERIC(8, 2),
    total_price          NUMERIC(8, 2),
    t_price_w_discount   NUMERIC(8, 2), /*total price with discount*/
    phone                VARCHAR(15),
    address              VARCHAR(255),
    delivery_required    BOOLEAN,
    delivery_details_id  BIGINT,
    order_status_id      INTEGER,
    created_at           TIMESTAMP,
    updated_at           TIMESTAMP,
    FOREIGN KEY (user_id)
        REFERENCES users (id),
    FOREIGN KEY (order_item_id)
        REFERENCES order_items (id),
    FOREIGN KEY (order_status_id)
        REFERENCES order_status (id),
    FOREIGN KEY (delivery_details_id)
        REFERENCES delivery_details (id)
);

/* Внешний ключ для деталей доставки*/
ALTER TABLE delivery_details
    ADD
        FOREIGN KEY (order_id) REFERENCES orders (id)
;

/* Внешний ключ для пользователей*/
ALTER TABLE users
    ADD
        FOREIGN KEY (order_id)
            REFERENCES orders (id)
;
/*Тут хранятся роли пользователей: админ, менеджер, клиент итп */
DROP TABLE IF EXISTS roles;
CREATE TABLE roles
(
    id   SERIAL,
    name VARCHAR(50) NOT NULL UNIQUE,
    PRIMARY KEY (id)
);

/* в этой таблице присваиваем роли пользователям */
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

/* Таблица storage отвечает за количество доступного товара на складе,
 * дату поставки, дату ожидаемой поставки.
 * Данные получаем по ID продукта*/
DROP TABLE IF EXISTS storage;
CREATE TABLE storage
(
    product_id             BIGSERIAL NOT NULL UNIQUE,
    quantity               INT,
    available_quantity     INT,
    date_of_delivery       TIMESTAMP,
    expected_delivery_date TIMESTAMP,
    updated_at             TIMESTAMP,
    FOREIGN KEY (product_id) REFERENCES products (id)
);



