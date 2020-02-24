package com.example.demo.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class Order {
    private Consumer consumer;
    private List<ItemOrder> orders;
}
