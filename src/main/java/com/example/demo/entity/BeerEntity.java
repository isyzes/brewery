package com.example.demo.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Data
@Entity
public class BeerEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String name;
    private String color;
    private double fortress;
    @JsonFormat(pattern = "dd.MM.yyyy")
    private LocalDate dateManufacture;
    private int shelfLife;
    private int costPrice;
    @OneToMany
    private List<PartRecipeEntity> recipe;
    private int litersInStock;
}
