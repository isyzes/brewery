package com.example.demo.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "ItemRecipe")
public class ItemRecipeEntity {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "ingredient_id")
    private IngredientEntity ingredientEntity;
    @Column(name = "milligram")
    private int milligram;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "beer_id")
    private BeerEntity beer;
}
