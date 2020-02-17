package com.example.demo.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class PartRecipeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @ManyToOne
    private IngredientEntity ingredientEntity;
    private int milligram;
}
