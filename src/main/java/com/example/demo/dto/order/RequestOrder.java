package com.example.demo.dto.order;

import com.example.demo.dto.Consumer;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class RequestOrder {
    private Consumer consumer;
    private List<OrderItem> items;
}

