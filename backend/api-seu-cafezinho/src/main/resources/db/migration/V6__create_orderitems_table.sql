CREATE TABLE tb_order_items (
    id SERIAL PRIMARY KEY,
    unit_price NUMERIC(10,2) NOT NULL,
    product_quantity INTEGER NOT NULL,
    order_id UUID NOT NULL,
    product_id BIGINT NOT NULL,
    CONSTRAINT fk_order_items_order FOREIGN KEY (order_id) REFERENCES tb_orders(id) ON DELETE CASCADE,
    CONSTRAINT fk_order_items_product FOREIGN KEY (product_id) REFERENCES tb_products(id) ON DELETE CASCADE
);