package com.example.demo.mapper;

import com.example.demo.dto.order.RequestOrder;
import com.example.demo.entity.OrderEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RequestOrderMapper extends DestinationMapper<RequestOrder, OrderEntity> {
}
