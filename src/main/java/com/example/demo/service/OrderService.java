package com.example.demo.service;

import com.example.demo.dto.order.OrderItem;
import com.example.demo.dto.order.RequestOrder;
import com.example.demo.dto.order.ResponseOrder;
import com.example.demo.entity.BeerEntity;
import com.example.demo.entity.OrderEntity;
import com.example.demo.entity.OrderItemEntity;
import com.example.demo.entity.UserEntity;
import com.example.demo.mapper.OrderItemMapper;
import com.example.demo.mapper.RequestOrderMapper;
import com.example.demo.mapper.ResponseOrderMapper;
import com.example.demo.repository.OrderItemRepository;
import com.example.demo.repository.OrderRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;

    private final RequestOrderMapper requestOrderMapper;
    private final ResponseOrderMapper responseOrderMapper;
    private final OrderItemMapper orderItemMapper;

    public ResponseOrder createOrder(final RequestOrder requestOrder, final List<BeerEntity> beerEntities, final UserEntity consumer) {
        final OrderEntity orderEntity = requestOrderMapper.sourceToDestination(requestOrder);
        final double totalPrice = getTotalPrice(requestOrder.getItems(), beerEntities);
        orderEntity.setPrice(totalPrice);
        orderEntity.setItems(getOrderItemEntity(requestOrder.getItems(), beerEntities));
        orderEntity.setConsumer(consumer);
        orderRepository.save(orderEntity);

        return responseOrderMapper.destinationToSource(orderEntity);
    }

    private double getTotalPrice(final List<OrderItem> products, final List<BeerEntity> beerEntities) {
        int totalPrice = 0;
        for (OrderItem order: products) {
            final BeerEntity beerEntity = getBeer(order.getBeer().getId(), beerEntities);

            totalPrice =+ order.getLiters() * beerEntity.getCostPrice();
        }

        BigDecimal bigDecimal = new BigDecimal(new BigInteger(String.valueOf(totalPrice)), 2);
        return bigDecimal.doubleValue();
    }

    private List<OrderItemEntity> getOrderItemEntity(final List<OrderItem> orderItems, final List<BeerEntity> beerEntities) {
        final List<OrderItemEntity> result = new ArrayList<>();

        for (OrderItem item: orderItems) {
            final OrderItemEntity orderItemEntity = orderItemMapper.sourceToDestination(item);
            final BeerEntity beerEntity = getBeer(item.getBeer().getId(), beerEntities);

            orderItemEntity.setBeer(beerEntity);
            result.add(orderItemEntity);
            orderItemRepository.save(orderItemEntity);
        }
        return result;
    }

    private BeerEntity getBeer(final long id, final List<BeerEntity> beerEntities) {
        return beerEntities.stream()
                .filter(beer -> beer.getId() == id)
                .findFirst()
                .get();
    }
}

