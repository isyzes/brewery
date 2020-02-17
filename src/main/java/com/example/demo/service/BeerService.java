package com.example.demo.service;

import com.example.demo.dto.*;
import com.example.demo.entity.BeerEntity;
import com.example.demo.entity.OrderEntity;
import com.example.demo.entity.PartRecipeEntity;
import com.example.demo.mapper.BeerRequestMapper;
import com.example.demo.mapper.OrderMapper;
import com.example.demo.mapper.PartRecipeMapper;
import com.example.demo.repository.BeerRepository;
import com.example.demo.repository.OrderRepository;
import lombok.AllArgsConstructor;
import org.decimal4j.util.DoubleRounder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class BeerService {

    private final BeerRepository beerRepository;

    private final WarehouseService warehouseService;
    private final ManagerService managerService;

    private final BeerRequestMapper beerRequestMapper;
    private final PartRecipeMapper partRecipeMapper;
    private final OrderMapper orderMapper;
    private final OrderRepository orderRepository;

    public Beer updatedBeer(final Beer newBeer, final long idBeer) {
        final Optional<BeerEntity> optionalBeer = beerRepository.findById(idBeer);

        if (optionalBeer.isPresent()) {
            final BeerEntity beerEntity = optionalBeer.get();
            updatedBeer(beerEntity, newBeer);

            beerRepository.save(beerEntity);
            return beerRequestMapper.destinationToSource(beerEntity);
        }
        return Beer.builder().build();
    }

    public List<Beer> getBeers() {
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

    @Transactional
    public void createdBeer(final OrderCreatedBeer orderCreatedBeer) {
        final long idBeer = orderCreatedBeer.getIdBeer();
        final int liters = orderCreatedBeer.getLiters();

        final Optional<BeerEntity> optionalBeer = beerRepository.findById(idBeer);

        if (optionalBeer.isPresent()) {
            final BeerEntity beerEntity = optionalBeer.get();

            final boolean thereIsIngredients = warehouseService.thereIsIngredients(beerEntity);

            if (thereIsIngredients) {
                warehouseService.takeIngredientsForBeer(beerEntity);

                beerEntity.setLitersInStock(beerEntity.getLitersInStock() + liters);

                beerRepository.save(beerEntity);
            } else {
                final Beer beer = beerRequestMapper.destinationToSource(beerEntity);
                managerService.needIngredientForBeer(beer);
            }
        }
    }

    @Transactional
    public OrderEntity sellBeer(final Order order) {

        final boolean thereIsBeer = warehouseService.thereIsBeer(order.getOrders());

       if (thereIsBeer) {
           final OrderEntity orderEntity = orderMapper.sourceToDestination(order);
           orderEntity.setPrice(totalPrice(order.getOrders()));

           warehouseService.sellBeer(orderEntity.getOrders());
           orderRepository.save(orderEntity);
           return orderEntity;
       } else {
            for (PartOrder part: order.getOrders()) {
                final OrderCreatedBeer orderCreatedBeer = new OrderCreatedBeer();
                orderCreatedBeer.setIdBeer(part.getBeer().getId());
                orderCreatedBeer.setLiters(part.getQuantity());

                createdBeer(orderCreatedBeer);
            }

            return sellBeer(order);
        }
    }


    private double totalPrice(final List<PartOrder> products) {
        int totalPrice = 0;
        for (PartOrder order: products)
            totalPrice =+ order.getQuantity() * order.getBeer().getCostPrice();

        return DoubleRounder.round(totalPrice/100.0, 2);
    }

    private void updatedBeer(final BeerEntity beerEntity, final Beer newBeer) {
        if (newBeer.getName() != null) beerEntity.setName(newBeer.getName());

        if (newBeer.getColor() != null) beerEntity.setColor(newBeer.getColor());

        if (newBeer.getFortress() > 0.0) beerEntity.setFortress(newBeer.getFortress());

        if (newBeer.getShelfLife() > 0) beerEntity.setShelfLife(newBeer.getShelfLife());

        if (newBeer.getCostPrice() > 0) beerEntity.setCostPrice(newBeer.getCostPrice());

        if (newBeer.getRecipe() != null) {
            //TODO придумать что нибудь другое
            final List<PartRecipeEntity> recipe = new ArrayList<>();
            for (PartRecipe part: newBeer.getRecipe())
                recipe.add(partRecipeMapper.sourceToDestination(part));

            beerEntity.getRecipe().clear();
            beerEntity.getRecipe().addAll(recipe);
        }
    }
}
