package com.example.demo.dto;

import lombok.Data;

@Data
public class OneOrder {
    private long id;
    private Beer beer;
    private int quantity;
}
