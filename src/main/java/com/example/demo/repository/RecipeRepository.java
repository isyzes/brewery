package com.example.demo.repository;

import com.example.demo.entity.RecipeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecipeRepository extends JpaRepository<RecipeEntity, Long> {
}
