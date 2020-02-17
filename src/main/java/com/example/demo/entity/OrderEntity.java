package com.example.demo.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
public class OrderEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private double price;
    @ManyToOne
    private ConsumerEntity consumer;
    @OneToMany
    private List<PartOrderEntity> orders;
}
