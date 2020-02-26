package com.example.demo.dto.order;

import com.example.demo.dto.beer.Beer;
import lombok.Data;

@Data
public class OrderItem {
    private Beer beer;
    private int liters;
}
