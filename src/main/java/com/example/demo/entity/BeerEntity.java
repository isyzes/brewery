package com.example.demo.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDate;

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
    private int shelfLife;//(дней)
    private int costPrice;//в копейках
//    private List<OneIngredient> recipe;
}
