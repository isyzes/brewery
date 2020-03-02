package com.example.demo.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;

@Data
@Entity
@Table(name = "beer")
public class BeerEntity {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "name")
    private String name;
    @Column(name = "color")
    private String color;
    @Column(name = "fortress")
    private double fortress;
    @Column(name = "date_manufacture")
    @JsonFormat(pattern = "dd.MM.yyyy")
    private LocalDate dateManufacture;
    @Column(name = "shelf_life")
    private int shelfLife;
    @Column(name = "cost_price")
    private int costPrice;
    @ManyToOne(optional = false)
    @JoinColumn(name = "recipe_id", nullable = false)
    private RecipeEntity recipe;
    @Column(name = "liters_in_stock")
    private int litersInStock;
}
