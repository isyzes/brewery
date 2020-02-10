package com.example.demo.service;

import com.example.demo.dto.Beer;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class BeerService {
    public Beer changeInformationBeer(Beer newBeer, long idBeer) {
        newBeer.setId(idBeer);
        newBeer.setColor("bright");
        newBeer.setFortress(12.5);
        newBeer.setDateManufacture(LocalDate.of(2020, 12, 12));
        newBeer.setShelfLife(25);
        return newBeer;
    }

    public List<Beer> getBeerList() {
        Beer first = Beer.builder()
                .id(1)
                .name("Garage")
                .color("bright")
                .fortress(12.5)
                .dateManufacture(LocalDate.of(2020,12,12))
                .shelfLife(25)
                .costPrice(574)
                .build();

        Beer second = Beer.builder()
                .id(2)
                .name("Miller")
                .color("bright")
                .fortress(12.5)
                .dateManufacture(LocalDate.of(2020,12,12))
                .shelfLife(25)
                .costPrice(755)
                .build();

        Beer third = Beer.builder()
                .id(3)
                .name("Heineken")
                .color("dark")
                .fortress(9.5)
                .dateManufacture(LocalDate.of(2020,12,12))
                .shelfLife(35)
                .costPrice(5756)
                .build();
        return List.of(first, second, third);
    }
}
