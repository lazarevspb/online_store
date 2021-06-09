/*
 * @author Valeriy Lazarev
 * @since 17.04.2021
 */

DROP TABLE IF EXISTS categories;
CREATE TABLE categories
(
    id          SERIAL PRIMARY KEY,
    title       VARCHAR(50) NOT NULL,
    description varchar(5000)
);

/*ссылается на таблицу с подробными характеристиками*/
DROP TABLE IF EXISTS details;
CREATE TABLE details
(
    id                 BIGSERIAL UNIQUE,
    category_id        INT,
    product_details_id INT,
--     PRIMARY KEY (id, category_id),
    PRIMARY KEY (id), /*Исправил, так как непонятно, зачем тут составной первичный ключь*/
    FOREIGN KEY (category_id)
        REFERENCES categories (id)
--     FOREIGN KEY (product_details_id)
--         REFERENCES products_details (id)
);

/*содержит список статуов продукта*/
DROP TABLE IF EXISTS status_products;
CREATE TABLE status_products
(
    id         SERIAL,
    title      VARCHAR(50) NOT NULL,
    updated_at TIMESTAMP DEFAULT current_timestamp,
    PRIMARY KEY (id)
);

/*содержит список продуктов*/
DROP TABLE IF EXISTS products;
CREATE TABLE products
(
    id          BIGSERIAL PRIMARY KEY,
    title       VARCHAR(128),
    price       NUMERIC(8, 2),
    status_id   INT, /*Статус продукта: Есть на складе, доступен для заказа, ожидается итп*/
    details_id  INT, /*Возможно, у продукта будут какие-то расширенные характеристики*/
    category_id INT,
    created_at  timestamp default current_timestamp,
    updated_at  timestamp default current_timestamp,
    FOREIGN KEY (category_id)
        REFERENCES categories (id),
    FOREIGN KEY (details_id)
        REFERENCES details (id),
    FOREIGN KEY (status_id)
        REFERENCES status_products (id)
);


/*Статус выполнения заказа, например: создан, оплачен, выполнен итд */
DROP TABLE IF EXISTS order_status;
CREATE TABLE order_status
(
    id         SERIAL,
    title      VARCHAR(50) NOT NULL,
    updated_at TIMESTAMP DEFAULT current_timestamp,
    PRIMARY KEY (id)
);

/*Пользователи*/
DROP TABLE IF EXISTS users;
CREATE TABLE users
(
    id         bigserial primary key,
    username   varchar(30) not null unique,
    phone      VARCHAR(15) UNIQUE,
    password   VARCHAR(100),
    email      VARCHAR(30) UNIQUE,
    first_name VARCHAR(50),
    last_name  VARCHAR(50),

    order_id   BIGINT,
    cart_id    BIGINT,
    enabled    BOOLEAN   DEFAULT true, /* пользователя можно будет деактивировать */
    created_at TIMESTAMP DEFAULT current_timestamp,
    updated_at TIMESTAMP DEFAULT current_timestamp
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
    actual          BOOLEAN   DEFAULT true,
    discount        NUMERIC(8, 2),
    product_id      BIGINT,
    category_id     INT,
    expiration_date TIMESTAMP,
    created_at      TIMESTAMP DEFAULT current_timestamp,
    updated_at      TIMESTAMP DEFAULT current_timestamp,
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
    updated_at         TIMESTAMP DEFAULT current_timestamp,

--     FOREIGN KEY (order_id) REFERENCES orders (id),
    FOREIGN KEY (status_delivery_id) REFERENCES delivery_status (id),
    PRIMARY KEY (id)
);

/*Элементы заказа, из них будем формировать заказ или корзину
  Самой корзины нет, так как в базе храниться не будет*/
DROP TABLE IF EXISTS order_items;
create table order_items
(
    id                bigserial primary key,
--     order_id          bigint references orders (id),
    order_id          bigint,
    product_id        bigint references products (id),
    title             varchar(255),
    quantity          int,
    price_per_product numeric(10, 2),
    price             numeric(10, 2),
    created_at        timestamp default current_timestamp,
    updated_at        timestamp default current_timestamp
);



/*В этой таблице будет храниться заказ, по ИД заказа можем получить юзера
  и статус самого заказа*/
DROP TABLE IF EXISTS orders;
CREATE TABLE orders
(
    id                   BIGSERIAL PRIMARY KEY,
    user_id              BIGINT,
--     cart_id              BIGINT,
    order_item_id        BIGINT,
    discount             NUMERIC(8, 2),
    promotional_event_id BIGINT,
    total_discount       NUMERIC(8, 2),
    total_price          NUMERIC(8, 2),
    t_price_w_discount   NUMERIC(8, 2), /*total price with discount*/
    phone                VARCHAR(15),
    address              VARCHAR(255),
    delivery_required    BOOLEAN   DEFAULT false,
    delivery_details_id  BIGINT,
    order_status_id      INTEGER,
    created_at           TIMESTAMP DEFAULT current_timestamp,
    updated_at           TIMESTAMP DEFAULT current_timestamp,
    FOREIGN KEY (user_id)
        REFERENCES users (id),
--     FOREIGN KEY (cart_id)
--         REFERENCES carts (id),
    FOREIGN KEY (order_item_id)
        REFERENCES order_items (id),
    FOREIGN KEY (order_status_id)
        REFERENCES order_status (id),
    FOREIGN KEY (delivery_details_id)
        REFERENCES delivery_details (id)
);



/* Внешний ключ для деталей доставки*/
ALTER TABLE order_items
    ADD
        FOREIGN KEY (order_id)
            REFERENCES orders (id)
;


/* Внешний ключ для деталей доставки*/
ALTER TABLE delivery_details
    ADD
        FOREIGN KEY (order_id)
            REFERENCES orders (id)
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
    PRIMARY KEY (user_id, role_id)
--     FOREIGN KEY (user_id)
--         REFERENCES users (id),
--     FOREIGN KEY (role_id)
--         REFERENCES roles (id)
);

/*Таблица содержит данные о хранилище продукта*/
DROP TABLE IF EXISTS storage_details;
CREATE TABLE storage_details
(
    id         BIGSERIAL PRIMARY KEY,
    name       VARCHAR(50),
    phone      VARCHAR(15) NOT NULL UNIQUE,
    email      VARCHAR(30) UNIQUE,
    address    VARCHAR(256) UNIQUE,
    enabled    BOOLEAN   DEFAULT true, /*склад может быть недоступен*/
    created_at TIMESTAMP DEFAULT current_timestamp,
    updated_at TIMESTAMP DEFAULT current_timestamp
);

/* Таблица storage отвечает за количество доступного товара на складе,
 * дату поставки, дату ожидаемой поставки.
 * Данные получаем по ID продукта*/
DROP TABLE IF EXISTS storage;
CREATE TABLE storage
(
    id                     BIGSERIAL PRIMARY KEY,
    product_id             BIGINT NOT NULL UNIQUE,
    quantity               INT,
    storage_details_id     INT,
    available_quantity     INT,
    date_of_delivery       TIMESTAMP,
    expected_delivery_date TIMESTAMP,
    updated_at             TIMESTAMP DEFAULT current_timestamp,
    FOREIGN KEY (product_id)
        REFERENCES products (id),
    FOREIGN KEY (storage_details_id)
        REFERENCES storage_details (id)
);

/*содержит имена стран производителей продукта*/
DROP TABLE IF EXISTS country;
CREATE TABLE country
(
    id   SERIAL PRIMARY KEY,
    name INT NOT NULL
);

/*содержит информацию о производителе*/
DROP TABLE IF EXISTS manufacturer;
CREATE TABLE manufacturer
(
    id          SERIAL PRIMARY KEY,
    name        INT NOT NULL,
    description VARCHAR(300),
    country_id  INT,
    FOREIGN KEY (country_id)
        REFERENCES country (id)
);

/* развернутые характеристики товара,
 * в зависимости от категории будут доступны те или иные поля*/
DROP TABLE IF EXISTS products_details;
CREATE TABLE products_details
(
    id              bigserial primary key,
    specification   VARCHAR(500),
    sex             VARCHAR(5),
    manufacturer_id INT,
    quality         VARCHAR(20),
    color           VARCHAR(5),
    width           NUMERIC(8, 2),
    height          NUMERIC(8, 2),
    weight          NUMERIC(8, 2),
    diagonal        NUMERIC(8, 2),
    size            BIGINT,
    created_at      TIMESTAMP DEFAULT current_timestamp,
    updated_at      TIMESTAMP DEFAULT current_timestamp
);

/* Внешний ключ для деталей продукта*/
ALTER TABLE details
    ADD
        FOREIGN KEY (product_details_id)
            REFERENCES products_details (id)
;

/*Изображения для товара*/
DROP TABLE IF EXISTS images;
CREATE TABLE images
(
    id   bigserial PRIMARY KEY,
    path varchar(255) NOT NULL
);

-- DROP TABLE IF EXISTS carts;
-- CREATE TABLE carts
-- (
--     id      BIGSERIAL PRIMARY KEY,
--     price   NUMERIC(8, 2),
--     user_id INT,
--     FOREIGN KEY (user_id)
--         REFERENCES users (id)
--
-- );


-- DROP TABLE IF EXISTS products_images_items;
-- CREATE TABLE cart_items
-- (
--     id                BIGSERIAL PRIMARY KEY,
--     cart_id           BIGINT REFERENCES carts (id),
--     product_id        BIGINT REFERENCES products (id),
--     title             VARCHAR(255),
--     quantity          INT,
--     price_per_product INT,
--     price             INT,
--     created_at        TIMESTAMP DEFAULT current_timestamp,
--     updated_at        TIMESTAMP DEFAULT current_timestamp
-- );


/*Промежуточная таблица для изображений*/
DROP TABLE IF EXISTS products_images_items;
CREATE TABLE products_images_items
(
    id         bigserial PRIMARY KEY,
    img_id     bigint NOT NULL,
    product_id bigint NOT NULL,
    FOREIGN KEY (product_id)
        REFERENCES products (id),
    FOREIGN KEY (img_id)
        REFERENCES images (id)
);

create table products_categories
(
    product_id  bigint,
    category_id bigint,
    foreign key (product_id) references products (id),
    foreign key (category_id) references categories (id)
);
