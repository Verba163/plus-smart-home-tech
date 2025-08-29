--DROP TABLE IF EXISTS shopping_cart;
--DROP TABLE IF EXISTS cart_item;

CREATE TABLE IF NOT EXISTS shopping_cart (
    id UUID PRIMARY KEY,
    user_name VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS cart_item (
    cart_id UUID NOT NULL,
    product_id UUID NOT NULL,
    quantity INTEGER NOT NULL CHECK (quantity > 0),
    PRIMARY KEY (cart_id, product_id),
    CONSTRAINT fk_shopping_cart
        FOREIGN KEY (cart_id)
        REFERENCES shopping_cart (id)
        ON DELETE CASCADE
);

CREATE INDEX IF NOT EXISTS idx_shopping_cart_user_name
    ON shopping_cart (user_name);