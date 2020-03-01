package com.example.demo.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "OrderItem")
public class OrderItemEntity {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @ManyToOne
    @JoinColumn(name = "beer_id")
    private BeerEntity beer;
    @Column(name = "liters")
    private int liters;
}


