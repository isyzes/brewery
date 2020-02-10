package com.example.demo.controller;

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
    @RequestMapping(value = "/buy/{idConsumers}",method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public Order buyBeer(@RequestBody List<Order.OneOrder> order, @PathVariable long idConsumers) {
        return service.buyBeer(order, idConsumers);
    }
}