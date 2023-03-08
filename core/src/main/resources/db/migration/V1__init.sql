CREATE TABLE products (
    id            bigserial  PRIMARY KEY,
    name          VARCHAR(255),
    price         numeric(8, 2),
    created_at    timestamp default current_timestamp,
    updated_at    timestamp default current_timestamp
);

CREATE TABLE orders
(
    id          bigserial primary key,
    username    VARCHAR (255) not null,
    total_price numeric(8, 2)    not null,
    created_at  timestamp default current_timestamp,
    updated_at  timestamp default current_timestamp
);

CREATE TABLE order_items
(
    id                bigserial primary key,
    product_id        bigint not null references products (id),
    order_id          bigint not null references orders (id),
    quantity          int    not null,
    price             numeric(8, 2)    not null,
    created_at        timestamp default current_timestamp,
    updated_at        timestamp default current_timestamp
);