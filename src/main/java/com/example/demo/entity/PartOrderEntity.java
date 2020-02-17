package com.example.demo.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class PartOrderEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @ManyToOne
    private BeerEntity beer;
    private int quantity;
}


