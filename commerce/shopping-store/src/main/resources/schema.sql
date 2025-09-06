DROP TABLE IF EXISTS product;

CREATE TABLE IF NOT EXISTS product (
    product_id UUID PRIMARY KEY,
    product_name VARCHAR(255) NOT NULL,
    description TEXT NOT NULL,
    image_src VARCHAR(255) NOT NULL,
    quantity_state VARCHAR(50) NOT NULL,
    product_state VARCHAR(50) NOT NULL,
    product_category VARCHAR(50) NOT NULL,
    price DOUBLE PRECISION NOT NULL CHECK (price >= 1)
);