package com.example.demo.service;

import com.example.demo.converter.PartRecipeConverter;
import com.example.demo.dto.*;
import com.example.demo.entity.BeerEntity;
import com.example.demo.entity.OrderEntity;
import com.example.demo.exception.UpdatedBeerException;
import com.example.demo.mapper.BeerRequestMapper;
import com.example.demo.mapper.OrderMapper;
import com.example.demo.repository.BeerRepository;
import com.example.demo.repository.OrderRepository;
import lombok.AllArgsConstructor;
import org.decimal4j.util.DoubleRounder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class BeerService {

    private final BeerRepository beerRepository;
    private final OrderRepository orderRepository;

    private final IngredientService warehouseService;
    private final ManagerService managerService;

    private final BeerRequestMapper beerRequestMapper;

    private final OrderMapper orderMapper;

    @Transactional
    public Beer updatedBeer(final Beer newBeer, final long idBeer) throws UpdatedBeerException {
        final Optional<BeerEntity> optionalBeer = beerRepository.findById(idBeer);

        if (optionalBeer.isPresent()) {
            final BeerEntity beerEntity = optionalBeer.get();
            updatedBeer(beerEntity, newBeer);

            beerRepository.save(beerEntity);
            return beerRequestMapper.destinationToSource(beerEntity);
        } else throw new UpdatedBeerException("Beer with id=" + idBeer + " not found!");

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
    public void updatedLitersInStockBeer(final OrderCreatedBeer orderCreatedBeer) {
        final long idBeer = orderCreatedBeer.getIdBeer();
        final int liters = orderCreatedBeer.getLiters();

        final Optional<BeerEntity> optionalBeer = beerRepository.findById(idBeer);

        if (optionalBeer.isPresent()) {
            final BeerEntity beerEntity = optionalBeer.get();
            final List<PartRecipe> recipe = PartRecipeConverter.destinationToSource(beerEntity.getRecipe());
            final boolean thereIsIngredients = warehouseService.thereIsIngredients(recipe);

            if (thereIsIngredients) {

                warehouseService.takeIngredientsForBeer(recipe);
                final int totalLitersInStock = beerEntity.getLitersInStock() + liters;
                beerEntity.setLitersInStock(totalLitersInStock);
                beerRepository.save(beerEntity);
            } else {
                managerService.setNeedIngredient(true);
            }
        }
    }

    @Transactional
    public OrderEntity sellBeer(final Order order) {

        final boolean thereIsBeer = thereIsBeer(order.getOrders());

       if (thereIsBeer) {
           final OrderEntity orderEntity = orderMapper.sourceToDestination(order);
           orderEntity.setPrice(totalPrice(order.getOrders()));

           sellBeer(order.getOrders());

           orderRepository.save(orderEntity);
           return orderEntity;
       } else {
           managerService.setNeedBeer(true);
           return new OrderEntity();
        }
    }



    private boolean thereIsBeer(final List<PartOrder> orders) {
        for (PartOrder part: orders) {
            final long id = part.getBeer().getId();

            final Optional<BeerEntity> optionalBeerEntity = beerRepository.findById(id);

            if (optionalBeerEntity.isPresent()) {
                final BeerEntity beerEntity = optionalBeerEntity.get();

                if (part.getQuantity() > beerEntity.getLitersInStock())
                    return false;

            } else return false;
        }

        return true;
    }

    private void sellBeer(final List<PartOrder> orders) {
        for (PartOrder part: orders) {
            final long id = part.getBeer().getId();
            final Optional<BeerEntity> optionalBeerEntity = beerRepository.findById(id);
            final BeerEntity beerEntity = optionalBeerEntity.get();
            final int totalLitersInStock = beerEntity.getLitersInStock() - part.getQuantity();
            beerEntity.setLitersInStock(totalLitersInStock);
            beerRepository.save(beerEntity);
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

        }
    }
}
