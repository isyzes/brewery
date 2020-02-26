package com.example.demo.dto.beer;

import lombok.Data;

@Data
public class ResponseUpdatedLitersBeer {
    private long idBeer;
    private String nameBeer;
    private int totalLiters;
}
