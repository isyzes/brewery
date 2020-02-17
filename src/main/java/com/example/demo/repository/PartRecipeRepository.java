package com.example.demo.repository;

import com.example.demo.entity.PartRecipeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PartRecipeRepository extends JpaRepository<PartRecipeEntity, Long> {
}
