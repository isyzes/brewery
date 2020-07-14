package com.example.demo.dto.order;

import lombok.Data;

import java.util.List;

@Data
public class ResponseOrder {
    private double price;
    private List<OrderItem> items;
}
