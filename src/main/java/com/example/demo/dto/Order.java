package com.example.demo.dto;

import lombok.Data;

import java.util.List;

@Data
public class Order {
    private long id;
    private double price;
    private Consumers consumers;
    private List<OneOrder> products;

    public Order(List<OneOrder> products) {
        this.products = products;
        double totalPrice = 0;
        for (OneOrder order: products)
            totalPrice =+ order.getQuantity() * order.getIdBeer();
        this.price = totalPrice;
    }

    @Data
    public static class OneOrder {
        private long id;
        private long idBeer;
        private int quantity;
    }
}
