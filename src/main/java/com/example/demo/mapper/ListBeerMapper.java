package com.example.demo.mapper;

import com.example.demo.dto.beer.Beer;
import com.example.demo.entity.BeerEntity;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ListBeerMapper extends DestinationMapper<List<Beer>, List<BeerEntity>> {
}
