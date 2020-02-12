package com.example.demo.controller;

import com.example.demo.dto.Beer;
import com.example.demo.dto.FinishedBeer;
import com.example.demo.service.BeerService;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Data
@RestController
@RequestMapping(value = "/beers/")
public class BeerController {
    private final BeerService service;

    @ResponseStatus(HttpStatus.OK)
    @PutMapping(value = "change/{idBeer}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Beer changeInformationBeer(@RequestBody Beer newBeer, @PathVariable long idBeer) {
        return service.changeInformationBeer(newBeer, idBeer);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "list")
    public List<Beer> getBeerList() {
        return service.getBeerList();
    }

    @ResponseStatus(HttpStatus.OK)
    @PostMapping(value = "make/{idBeer}?quantity={quantity}")
    public void makeBeer(@PathVariable long idBeer, @PathVariable int quantity) {
        service.makeBeer(idBeer, quantity);
    }
}
