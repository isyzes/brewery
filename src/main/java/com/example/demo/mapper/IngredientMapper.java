package com.example.demo.mapper;

import com.example.demo.dto.ingredient.Ingredient;
import com.example.demo.entity.IngredientEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface IngredientMapper extends DestinationMapper<Ingredient, IngredientEntity> {

}
