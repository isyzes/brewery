package com.example.demo.mapper;

import com.example.demo.dto.PartRecipe;
import com.example.demo.entity.PartRecipeEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PartRecipeMapper extends DestinationMapper<PartRecipe, PartRecipeEntity> {
}
