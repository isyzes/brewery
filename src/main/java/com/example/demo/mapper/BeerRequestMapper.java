package com.example.demo.mapper;

import com.example.demo.dto.Beer;
import com.example.demo.entity.BeerEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BeerRequestMapper extends DestinationMapper<Beer, BeerEntity> {
}
