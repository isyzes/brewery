package com.example.demo.mapper;

import com.example.demo.dto.order.OrderItem;
import com.example.demo.entity.OrderItemEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface OrderItemMapper extends DestinationMapper<OrderItem, OrderItemEntity>{
}
