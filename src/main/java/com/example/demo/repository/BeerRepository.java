package com.example.demo.repository;

import com.example.demo.entity.BeerEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BeerRepository extends JpaRepository<BeerEntity, Long> {
}
