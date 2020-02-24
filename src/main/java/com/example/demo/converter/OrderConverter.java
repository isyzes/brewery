package com.example.demo.converter;

import com.example.demo.dto.Beer;
import com.example.demo.dto.Consumer;
import com.example.demo.dto.ItemOrder;
import com.example.demo.dto.Order;
import com.example.demo.entity.BeerEntity;
import com.example.demo.entity.ItemOrderEntity;
import com.example.demo.entity.OrderEntity;
import com.example.demo.entity.UserEntity;

import java.util.ArrayList;
import java.util.List;

public class OrderConverter {
    public static OrderEntity destinationToSource(Order order) {
        OrderEntity orderEntity = new OrderEntity();

        orderEntity.setConsumer(destinationToSource(order.getConsumer()));
        orderEntity.setOrders(destinationToSource(order.getOrders()));

        return orderEntity;
    }

    private static UserEntity destinationToSource(Consumer consumer) {
        UserEntity userEntity = new UserEntity();

        userEntity.setId(consumer.getId());
        userEntity.setFio(consumer.getFio());
        return userEntity;
    }

    private static List<ItemOrderEntity> destinationToSource(List<ItemOrder> itemOrders) {
        List<ItemOrderEntity> result = new ArrayList<>();

        for (ItemOrder item: itemOrders) {
            ItemOrderEntity itemOrderEntity = new ItemOrderEntity();
            itemOrderEntity.setId(item.getId());
            itemOrderEntity.setBeer(destinationToSource(item.getBeer()));
            itemOrderEntity.setLiters(item.getLiters());
            result.add(itemOrderEntity);
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
