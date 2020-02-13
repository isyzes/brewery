package com.example.demo.service;

import com.example.demo.dto.Beer;
import com.example.demo.dto.FinishedBeer;
import com.example.demo.entity.BeerEntity;
import com.example.demo.entity.FinishedBeerEntity;
import com.example.demo.entity.WarehouseEntity;
import com.example.demo.repository.BeerRepository;
import com.example.demo.repository.FinishedBeerRepository;
import com.example.demo.repository.WarehouseRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class BeerService {

    private final BeerRepository beerRepository;
    private final WarehouseRepository warehouseRepository;
    private final FinishedBeerRepository finishedBeerRepository;

    public Beer changeInformationBeer(Beer newBeer, long idBeer) {
        Optional<BeerEntity> optionalBeer = beerRepository.findById(idBeer);

        if (optionalBeer.isEmpty()) {
            //Кинуть Exception

            return Beer.builder().build();
        }
        else {
            BeerEntity beer = optionalBeer.get();
            beer.setName(newBeer.getName());
            beer.setCostPrice(newBeer.getCostPrice());

            beerRepository.save(beer);

            return Beer.builder()
                    .id(beer.getId())
                    .name(beer.getName())
                    .color(beer.getColor())
                    .costPrice(beer.getCostPrice())
                    .dateManufacture(beer.getDateManufacture())
                    .fortress(beer.getFortress())
                    .shelfLife(beer.getShelfLife())
                    .build();
        }

    }

    public List<Beer> getBeerList() {
        return beerRepository.findAll().stream().map(
                beerEntity -> Beer.builder()
                        .id(beerEntity.getId())
                        .name(beerEntity.getName())
                        .color(beerEntity.getColor())
                        .fortress(beerEntity.getFortress())
                        .dateManufacture(beerEntity.getDateManufacture())
                        .shelfLife(beerEntity.getShelfLife())
                        .costPrice(beerEntity.getCostPrice())
                        .build())
                .collect(Collectors.toList());
    }

    public void makeBeer(long idBeer, int quantity) {
        Optional<BeerEntity> beer = beerRepository.findById(idBeer);

        if (beer.isEmpty()) {
            //todo кинуть exception
        } else {
            //todo сделать запрос на скалад, есть ли ингридиееты, если есть - отпрать на склад готовое пиво

            WarehouseEntity warehouseEntity = warehouseRepository.findAll().get(0);

            if (warehouseEntity == null) {
                warehouseEntity = new WarehouseEntity();
                warehouseEntity.setFinishedBeerEntitiesList(new ArrayList<>());

                warehouseRepository.save(warehouseEntity);
            }

            FinishedBeerEntity finishedBeerEntity = new FinishedBeerEntity();
            finishedBeerEntity.setBeer(beer.get());
            finishedBeerEntity.setQuantity(quantity);

            finishedBeerRepository.save(finishedBeerEntity);

            warehouseEntity.getFinishedBeerEntitiesList().add(finishedBeerEntity);

            warehouseRepository.save(warehouseEntity);
        }
    }
}
