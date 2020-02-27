package com.example.demo.mapper;

import com.example.demo.dto.recipe.RecipeItem;
import com.example.demo.entity.RecipeItemEntity;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ListRecipeItemMapper extends DestinationMapper<List<RecipeItem>, List<RecipeItemEntity>> {
}
