package com.example.demo.controller;

import com.example.demo.dto.ingredient.IngredientRequestOrder;
import com.example.demo.dto.ingredient.IngredientResponseOrder;
import com.example.demo.exception.BreweryIngredientException;
import com.example.demo.service.IngredientService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/ingredients/")
public class IngredientController {
    private final IngredientService service;

    @ResponseStatus(HttpStatus.OK)
    @PutMapping(value = "buy", consumes = MediaType.APPLICATION_JSON_VALUE)
    public IngredientResponseOrder buyIngredient(@RequestBody final IngredientRequestOrder ingredientRequestOrder) throws BreweryIngredientException {
        return service.buyIngredient(ingredientRequestOrder);
    }
}
