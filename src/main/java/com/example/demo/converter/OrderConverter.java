package com.example.demo.converter;

import com.example.demo.dto.Beer;
import com.example.demo.dto.Consumer;
import com.example.demo.dto.OrderItem;
import com.example.demo.dto.Order;
import com.example.demo.entity.BeerEntity;
import com.example.demo.entity.OrderItemEntity;
import com.example.demo.entity.OrderEntity;
import com.example.demo.entity.UserEntity;

import java.util.ArrayList;
import java.util.List;

public class OrderConverter {
    public static OrderEntity destinationToSource(Order order) {
        OrderEntity orderEntity = new OrderEntity();

        orderEntity.setConsumer(destinationToSource(order.getConsumer()));
        orderEntity.setItem(destinationToSource(order.getItems()));

        return orderEntity;
    }

    private static UserEntity destinationToSource(Consumer consumer) {
        UserEntity userEntity = new UserEntity();

        userEntity.setId(consumer.getId());
        userEntity.setFio(consumer.getFio());
        return userEntity;
    }

    private static List<OrderItemEntity> destinationToSource(List<OrderItem> orderItems) {
        List<OrderItemEntity> result = new ArrayList<>();

        for (OrderItem item: orderItems) {
            OrderItemEntity orderItemEntity = new OrderItemEntity();
            orderItemEntity.setId(item.getId());
            orderItemEntity.setBeer(destinationToSource(item.getBeer()));
            orderItemEntity.setLiters(item.getLiters());
            result.add(orderItemEntity);
        }
        return result;
    }

    private static BeerEntity destinationToSource(Beer beer) {
        BeerEntity beerEntity = new BeerEntity();
        beerEntity.setId(beer.getId());
        beerEntity.setCostPrice(beer.getCostPrice());
        return beerEntity;
    }
}
