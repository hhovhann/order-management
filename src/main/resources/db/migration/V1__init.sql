-- V1__init.sql
-- Initial database schema for Pilotes Order Management System

CREATE TABLE IF NOT EXISTS customer
(
    id           UUID PRIMARY KEY    NOT NULL,
    first_name   VARCHAR(200)        NOT NULL,
    last_name    VARCHAR(200)        NOT NULL,
    phone_number VARCHAR(50) UNIQUE  NOT NULL,
    email        VARCHAR(255) UNIQUE NOT NULL,
    created_at   TIMESTAMP,
    updated_at   TIMESTAMP
);

CREATE TABLE IF NOT EXISTS orders
(
    id                UUID PRIMARY KEY    NOT NULL,
    street            VARCHAR(255)   NOT NULL,
    postcode          VARCHAR(50)    NOT NULL,
    city              VARCHAR(100)   NOT NULL,
    country           VARCHAR(100)   NOT NULL,
    address_number    INT,
    number_of_pilotes INT            NOT NULL,
    price_per_order   DECIMAL(10, 2) NOT NULL,
    total_price       DECIMAL(10, 2) NOT NULL,
    creation_time     TIMESTAMP,
    update_time       TIMESTAMP,
    customer_id       UUID,
    cancelled         BOOLEAN        NOT NULL DEFAULT FALSE,
    CONSTRAINT fk_orders_customer FOREIGN KEY (customer_id) REFERENCES customer (id)
);

-- Indexes for performance optimization
CREATE INDEX idx_customer_first_name ON customer (first_name);
CREATE INDEX idx_customer_last_name ON customer (last_name);
CREATE INDEX idx_customer_email ON customer (email);
CREATE INDEX idx_customer_phone_number ON customer (phone_number);

CREATE INDEX idx_orders_customer_id ON orders (customer_id);
CREATE INDEX idx_orders_cancelled ON orders (cancelled);
CREATE INDEX idx_orders_creation_time ON orders (creation_time);
