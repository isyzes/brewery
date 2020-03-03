package com.example.demo.dto.order;

import lombok.Data;

import java.util.List;

@Data
public class AdminResponseOrder {
    private long id;
    private double price;
    private long user_id;
    private List<AdminOrderItem> item;
}
