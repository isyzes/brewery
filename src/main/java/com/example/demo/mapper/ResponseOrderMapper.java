package com.example.demo.mapper;

import com.example.demo.dto.order.ResponseOrder;
import com.example.demo.entity.OrderEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ResponseOrderMapper extends DestinationMapper<ResponseOrder, OrderEntity> {
}
