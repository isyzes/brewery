package com.example.demo.dto.order;

import com.example.demo.dto.Consumer;
import lombok.Data;

import java.util.List;

@Data
public class ResponseOrder {
    private double price;
    private Consumer consumer;
    private List<OrderItem> items;
}
