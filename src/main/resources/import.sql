INSERT INTO products (id, name, price, available_quantity) VALUES (1, 'Laptop', 2800.00, 2), (2, 'Mouse', 80.00, 5);

INSERT INTO orders (id) VALUES (1);

INSERT INTO order_product (order_id, product_id, quantity) VALUES (1, 1, 2), (1, 2, 1);
