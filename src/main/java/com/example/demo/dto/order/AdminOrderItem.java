package com.example.demo.dto.order;

import lombok.Data;

@Data
public class AdminOrderItem {
    private long id;
    private long beer_id;
    private int liters;
    private long order_id;
}
