package org.banco.entity;

import lombok.Data;

import java.util.List;

@Data
public class Order {
    private Long id;
    private List<Product> products;
}
