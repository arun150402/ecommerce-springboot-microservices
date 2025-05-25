CREATE DATABASE IF NOT EXISTS order_service;
CREATE TABLE IF NOT EXISTS order_service.t_orders (
     id INT(20) NOT NULL AUTO_INCREMENT,
     order_number VARCHAR(255) DEFAULT NULL,
     sku_code VARCHAR(255),
     price DECIMAL(19, 2),
     quantity INT(11),
    PRIMARY KEY (id)
);