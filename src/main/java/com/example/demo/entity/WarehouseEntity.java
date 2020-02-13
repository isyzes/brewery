package com.example.demo.entity;

import com.example.demo.dto.Ingredient;
import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
public class WarehouseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToMany
    private List<FinishedBeerEntity> finishedBeerEntitiesList;

}
