CREATE TABLE tb_orders (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    total_price NUMERIC(10,2),
    order_status VARCHAR(50) NOT NULL,
    delivery_method VARCHAR(50) NOT NULL,
    payment_method VARCHAR(50) NOT NULL,
    created_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_date TIMESTAMP,
    user_id UUID NOT NULL,
    address_id UUID,
    CONSTRAINT fk_orders_user FOREIGN KEY (user_id) REFERENCES tb_users(id) ON DELETE CASCADE,
    CONSTRAINT fk_orders_address FOREIGN KEY (address_id) REFERENCES tb_addresses(id) ON DELETE SET NULL
);