DROP TABLE IF EXISTS delivery;
DROP TABLE IF EXISTS delivery_address;

CREATE TABLE delivery_address (
    address_id UUID PRIMARY KEY,
    country VARCHAR(255),
    city VARCHAR(255),
    street VARCHAR(255),
    house VARCHAR(255),
    flat VARCHAR(255)
);

CREATE TABLE delivery (
    delivery_id UUID PRIMARY KEY,
    from_address_id UUID UNIQUE,
    to_address_id UUID UNIQUE,
    order_id UUID,
    delivery_state VARCHAR(50),
    weight DOUBLE PRECISION,
    volume DOUBLE PRECISION,
    fragile BOOLEAN,
    CONSTRAINT fk_from_address FOREIGN KEY (from_address_id) REFERENCES delivery_address(address_id),
    CONSTRAINT fk_to_address FOREIGN KEY (to_address_id) REFERENCES delivery_address(address_id)
);
