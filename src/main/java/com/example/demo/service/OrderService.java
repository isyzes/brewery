package com.example.demo.service;

import com.example.demo.dto.order.OrderItem;
import com.example.demo.dto.order.RequestOrder;
import com.example.demo.dto.order.ResponseOrder;
import com.example.demo.entity.BeerEntity;
import com.example.demo.entity.OrderEntity;
import com.example.demo.entity.OrderItemEntity;
import com.example.demo.exception.BreweryBeerException;
import com.example.demo.mapper.RequestOrderMapper;
import com.example.demo.mapper.ResponseOrderMapper;
import com.example.demo.repository.BeerRepository;
import com.example.demo.repository.OrderItemRepository;
import com.example.demo.repository.OrderRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Service
public class OrderService {
    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;

    private final RequestOrderMapper requestOrderMapper;
    private final ResponseOrderMapper responseOrderMapper;

    private final BeerRepository beerRepository;


    public ResponseOrder getOrder(final RequestOrder requestOrder) {
        final OrderEntity orderEntity = requestOrderMapper.sourceToDestination(requestOrder);
        final double totalPrice = totalPrice(requestOrder.getItems());
        orderEntity.setPrice(totalPrice);
        orderEntity.setItems(getOrderItemEntity(requestOrder.getItems(), orderEntity));
        orderRepository.save(orderEntity);

        return responseOrderMapper.destinationToSource(orderEntity);
    }

    private double totalPrice(final List<OrderItem> products) {
        int totalPrice = 0;
        for (OrderItem order: products) {
            BeerEntity beerEntity = beerRepository.findById(order.getBeer().getId()).get();
            totalPrice =+ order.getLiters() * beerEntity.getCostPrice();
        }

        BigDecimal bigDecimal = new BigDecimal(new BigInteger(String.valueOf(totalPrice)), 2);
        return bigDecimal.doubleValue();
    }

    private List<OrderItemEntity> getOrderItemEntity(final List<OrderItem> orderItems, final OrderEntity orderEntity) {
        List<OrderItemEntity> result = new ArrayList<>();
        for (OrderItem item: orderItems) {
            OrderItemEntity orderItemEntity = new OrderItemEntity();
            orderItemEntity.setLiters(item.getLiters());
            orderItemEntity.setOrder(orderEntity);
            BeerEntity beerEntity = beerRepository.findById(item.getBeer().getId()).get();
            orderItemEntity.setBeer(beerEntity);
            result.add(orderItemEntity);

            orderItemRepository.save(orderItemEntity);
        }
        return result;
    }
}
