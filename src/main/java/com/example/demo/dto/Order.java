package com.example.demo.dto;

import lombok.Data;

import java.util.List;

@Data
public class Order {
    private long id;
    private double price;
    private Consumers consumers;
    private List<OneOrder> orders;

    public Order(List<OneOrder> products) {
        this.orders = products;
        double totalPrice = 0;
        for (OneOrder order: products)
            totalPrice =+ order.getQuantity() * order.getIdBeer();
        this.price = totalPrice;
    }
}
