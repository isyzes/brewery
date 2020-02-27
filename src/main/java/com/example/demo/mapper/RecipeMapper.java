package com.example.demo.mapper;

import com.example.demo.dto.recipe.Recipe;
import com.example.demo.entity.RecipeEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RecipeMapper extends DestinationMapper<Recipe, RecipeEntity> {
}
