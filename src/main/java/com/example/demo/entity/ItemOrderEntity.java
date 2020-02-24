package com.example.demo.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "ItemOrder")
public class ItemOrderEntity {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @ManyToOne
    @JoinColumn(name = "beer_id")
    private BeerEntity beer;
    @Column(name = "liters")
    private int liters;
    @ManyToOne
    @JoinColumn(name = "order_id")
    private OrderEntity order;
}


