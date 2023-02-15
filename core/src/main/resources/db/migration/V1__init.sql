CREATE TABLE products (
    id            bigserial  PRIMARY KEY,
    name          VARCHAR(255),
    price         float,
    created_at    timestamp default current_timestamp,
    updated_at    timestamp default current_timestamp
);

CREATE TABLE orders
(
    id          bigserial primary key,
    username    VARCHAR (255) not null,
    total_price float    not null,
    created_at  timestamp default current_timestamp,
    updated_at  timestamp default current_timestamp
);

CREATE TABLE order_items
(
    id                bigserial primary key,
    product_id        bigint not null references products (id),
    order_id          bigint not null references orders (id),
    quantity          int    not null,
    price             float    not null,
    created_at        timestamp default current_timestamp,
    updated_at        timestamp default current_timestamp
);