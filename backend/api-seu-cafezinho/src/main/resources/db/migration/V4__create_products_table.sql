CREATE TABLE tb_products (
    id SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    image_url TEXT NOT NULL,
    description TEXT NOT NULL,
    price NUMERIC(5,2) NOT NULL,
    created_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_date TIMESTAMP,
    category_id INT,
    CONSTRAINT fk_products_category FOREIGN KEY (category_id) REFERENCES tb_categories(id) ON DELETE SET NULL
);