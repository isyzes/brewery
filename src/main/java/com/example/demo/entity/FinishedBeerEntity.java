package com.example.demo.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class FinishedBeerEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToOne
    private BeerEntity beer;
    private int quantity;
}
