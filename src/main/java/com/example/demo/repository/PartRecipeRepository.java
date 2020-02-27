package com.example.demo.repository;

import com.example.demo.entity.RecipeItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PartRecipeRepository extends JpaRepository<RecipeItemEntity, Long> {
}
