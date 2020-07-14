package com.example.demo.controller;

import com.example.demo.dto.beer.Beer;
import com.example.demo.dto.order.RequestOrder;
import com.example.demo.dto.beer.OrderCreatedBeer;
import com.example.demo.dto.beer.ResponseUpdatedLitersBeer;
import com.example.demo.dto.order.ResponseOrder;
import com.example.demo.entity.AuthInfoEntity;
import com.example.demo.entity.UserEntity;
import com.example.demo.exception.BreweryBeerException;
import com.example.demo.exception.BreweryIngredientException;
import com.example.demo.exception.BreweryUpdatedBeerException;
import com.example.demo.service.BeerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/beers/")
public class BeerController {

    private final BeerService service;

    @ResponseStatus(HttpStatus.OK)
    @PutMapping(value = "updated/{idBeer}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Beer updatedBeer(@RequestBody final Beer newBeer, @PathVariable final long idBeer) throws BreweryUpdatedBeerException {
        return service.updatedBeer(newBeer, idBeer);
    }

    @ResponseStatus(HttpStatus.OK)
    @PutMapping(value = "updated")
    public ResponseUpdatedLitersBeer updatedLitersBeerInStock(@RequestBody final OrderCreatedBeer orderCreatedBeer) throws BreweryIngredientException, BreweryBeerException {
        return service.updatedLitersInStockBeer(orderCreatedBeer);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "list")
    public List<Beer> getBeers() {
        return service.getBeers();
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(value = "buy", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseOrder createOrder(@RequestBody final RequestOrder requestOrder, @AuthenticationPrincipal final AuthInfoEntity authInfoEntity) throws BreweryBeerException {
        return service.createOrder(requestOrder, authInfoEntity);
    }
}
