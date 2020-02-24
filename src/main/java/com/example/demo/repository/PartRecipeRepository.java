package com.example.demo.repository;

import com.example.demo.entity.ItemRecipeEntity;
import org.springframework.data.jpa.repository.JpaRepository;


public interface PartRecipeRepository extends JpaRepository<ItemRecipeEntity, Long> {
}
