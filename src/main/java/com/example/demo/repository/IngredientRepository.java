package com.example.demo.repository;

import com.example.demo.entity.IngredientEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


public interface IngredientRepository extends JpaRepository<IngredientEntity, Long> {
}
