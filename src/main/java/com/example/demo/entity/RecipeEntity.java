package com.example.demo.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@Table(name = "Recipe")
public class RecipeEntity {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "recipe")
    private List<RecipeItemEntity> items;
}
