package com.example.demo.controller;

import com.example.demo.dto.OneOrder;
import com.example.demo.dto.Order;
import com.example.demo.service.SalesService;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Data
@RestController
public class SalesController {
    private final SalesService service;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(value = "/sell/{idConsumers}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Order buyBeer(@RequestBody List<OneOrder> listOrder, @PathVariable long idConsumers) {
        return service.sellBeer(listOrder, idConsumers);
    }
}
