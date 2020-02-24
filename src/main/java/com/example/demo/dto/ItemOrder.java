package com.example.demo.dto;

import lombok.Data;

@Data
public class ItemOrder {
    private long id;
    private Beer beer;
    private int liters;
}
