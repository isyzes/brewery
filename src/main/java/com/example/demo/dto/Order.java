package com.example.demo.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class Order {
    private long id;
    private double price;
    private Consumer consumers;
    private List<OneOrder> listOrder;
}
