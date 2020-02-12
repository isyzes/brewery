package com.example.demo.service;

import com.example.demo.dto.Consumer;
import com.example.demo.dto.OneOrder;
import com.example.demo.dto.Order;
import org.decimal4j.util.DoubleRounder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SalesService {
    public Order sellBeer(List<OneOrder> listOrder, long idConsumers) {
        Consumer consumer = Consumer.builder().id(idConsumers).build();

        return Order.builder()
                .id(4)
                .consumers(consumer)
                .price(totalPrice(listOrder))
                .listOrder(listOrder)
                .build();
    }

    private double totalPrice(List<OneOrder> products) {
        int totalPrice = 0;
        for (OneOrder order: products)
            totalPrice =+ order.getQuantity() * order.getBeer().getCostPrice();

        return DoubleRounder.round(totalPrice/100.0, 2);
    }

}
