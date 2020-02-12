package com.example.demo.dto;

import lombok.Data;

@Data
public class Ingredient {
    private long id;
    private String name;
    private double price;
    private String manufacturer;
}
