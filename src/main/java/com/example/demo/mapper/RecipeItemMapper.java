package com.example.demo.mapper;

import com.example.demo.dto.recipe.RecipeItem;
import com.example.demo.entity.RecipeItemEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RecipeItemMapper extends DestinationMapper<RecipeItem, RecipeItemEntity> {
}
