package com.example.demo.converter;

import com.example.demo.dto.Consumer;
import com.example.demo.dto.order.OrderItem;
import com.example.demo.dto.order.RequestOrder;
import com.example.demo.entity.BeerEntity;
import com.example.demo.entity.OrderItemEntity;
import com.example.demo.entity.OrderEntity;
import com.example.demo.entity.UserEntity;

import java.util.ArrayList;
import java.util.List;

import static com.example.demo.security.Roles.CONSUMER;

public class OrderConverter {
    public static OrderEntity destinationToSource(RequestOrder requestOrder) {
        OrderEntity orderEntity = new OrderEntity();

        orderEntity.setConsumer(destinationToSource(requestOrder.getConsumer()));
//        orderEntity.setItem(destinationToSource(requestOrder.getItems()));

        return orderEntity;
    }

    private static UserEntity destinationToSource(Consumer consumer) {
        UserEntity userEntity = new UserEntity();
        userEntity.setUserRole(CONSUMER);
        userEntity.setId(consumer.getId());
        userEntity.setFio(consumer.getFio());
        return userEntity;
    }

    private static List<OrderItemEntity> destinationToSource(List<OrderItem> orderItems) {
        List<OrderItemEntity> result = new ArrayList<>();

        for (OrderItem item: orderItems) {
            OrderItemEntity orderItemEntity = new OrderItemEntity();

//            orderItemEntity.setBeer(destinationToSource(item.getIdBeer()));
            orderItemEntity.setLiters(item.getLiters());
            result.add(orderItemEntity);
        }
        return result;
    }

    private static BeerEntity destinationToSource(long id) {
        BeerEntity beerEntity = new BeerEntity();
        beerEntity.setId(id);
        return beerEntity;
    }
}
