package com.example.demo.controller;

import com.example.demo.service.PurchasesService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
public class IngredientController {
    private final PurchasesService service;
    @ResponseStatus(HttpStatus.OK)
    @PostMapping(value = "/buy/{idIngredient}?quantity={quantity}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void buyIngredient(@PathVariable long idIngredient, @PathVariable String quantity) {
        service.buyIngredient(idIngredient, quantity);
    }
}
