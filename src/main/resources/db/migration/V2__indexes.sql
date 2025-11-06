-- V2__indexes.sql
-- Performance indexes and tuning for Pilotes Order Management

CREATE INDEX IF NOT EXISTS idx_orders_total_price ON orders (total_price);
CREATE INDEX IF NOT EXISTS idx_orders_city_country ON orders (city, country);
CREATE INDEX IF NOT EXISTS idx_customer_name ON customer (first_name, last_name);

-- Composite index to speed up statistics query
CREATE INDEX IF NOT EXISTS idx_orders_customer_id_totalprice ON orders (customer_id, total_price);
