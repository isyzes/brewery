package com.example.demo.controller;

import com.example.demo.dto.Beer;
import com.example.demo.dto.Order;
import com.example.demo.dto.OrderCreatedBeer;
import com.example.demo.entity.OrderEntity;
import com.example.demo.exception.UpdatedBeerException;
import com.example.demo.service.BeerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/beers/")
public class BeerController {
    private final BeerService service;

    @ResponseStatus(HttpStatus.OK)
    @PutMapping(value = "updated/{idBeer}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Beer updatedBeer(@RequestBody final Beer newBeer, @PathVariable final long idBeer) throws UpdatedBeerException {
        //https://github.com/wgdetective/java-training-example/blob/origin/feature/lesson_5/src/main/java/com/gpsolutions/edu/java/training/example/controller/ExceptionControllerAdvice.java
        return service.updatedBeer(newBeer, idBeer);
    }

    @ResponseStatus(HttpStatus.OK)
    @PutMapping(value = "updated")
    public void updatedLitersInStockBeer(@RequestBody final OrderCreatedBeer orderCreatedBeer) {
        service.updatedLitersInStockBeer(orderCreatedBeer);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "list")
    public List<Beer> getBeers() {
        return service.getBeers();
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(value = "sell", consumes = MediaType.APPLICATION_JSON_VALUE)
    public OrderEntity sellBeer(@RequestBody final Order order) {
        //вернуть другую сущность
        return service.sellBeer(order);
    }
}
