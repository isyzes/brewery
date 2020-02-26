package com.example.demo.service;

import com.example.demo.converter.OrderConverter;
import com.example.demo.converter.PartRecipeConverter;
import com.example.demo.dto.*;
import com.example.demo.dto.beer.Beer;
import com.example.demo.dto.beer.OrderCreatedBeer;
import com.example.demo.dto.beer.ResponseUpdatedLitersBeer;
import com.example.demo.dto.order.Order;
import com.example.demo.dto.order.OrderItem;
import com.example.demo.dto.order.ResponseOrder;
import com.example.demo.entity.BeerEntity;
import com.example.demo.entity.OrderEntity;
import com.example.demo.exception.NeedBeerException;
import com.example.demo.exception.UpdatedBeerException;
import com.example.demo.mapper.BeerRequestMapper;
import com.example.demo.mapper.OrderMapper;
import com.example.demo.repository.BeerRepository;
import com.example.demo.repository.OrderRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.BigInteger;
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
    public ResponseUpdatedLitersBeer updatedLitersInStockBeer(final OrderCreatedBeer orderCreatedBeer) {
        final long idBeer = orderCreatedBeer.getIdBeer();
        final int liters = orderCreatedBeer.getLiters();
        final ResponseUpdatedLitersBeer response = new ResponseUpdatedLitersBeer();
        response.setIdBeer(idBeer);

        final Optional<BeerEntity> optionalBeer = beerRepository.findById(idBeer);

        if (optionalBeer.isPresent()) {
            final BeerEntity beerEntity = optionalBeer.get();
            final List<RecipeItem> recipe = PartRecipeConverter.destinationToSource(beerEntity.getRecipe().getItems());
            final boolean thereIsIngredients = warehouseService.thereIsIngredients(recipe);

            response.setNameBeer(beerEntity.getName());

            if (thereIsIngredients) {

                warehouseService.takeIngredientsForBeer(recipe);
                final int totalLitersInStock = beerEntity.getLitersInStock() + liters;

                response.setTotalLiters(totalLitersInStock);

                beerEntity.setLitersInStock(totalLitersInStock);
                beerRepository.save(beerEntity);
            } else {
                managerService.setNeedIngredient(true);
            }
        }

        return response;
    }

    @Transactional
    public ResponseOrder sellBeer(final Order order) throws NeedBeerException {


        final boolean thereIsBeer = thereIsBeer(order.getItems());

       if (thereIsBeer) {
           final OrderEntity orderEntity = OrderConverter.destinationToSource(order);
           final double totalPrice = totalPrice(order.getItems());

           orderEntity.setPrice(totalPrice);
           sellBeer(order.getItems());

           orderRepository.save(orderEntity);

           final ResponseOrder response = new ResponseOrder();
           response.setPrice(totalPrice);
           response.setConsumer(order.getConsumer());
           response.setItems(order.getItems());
           return response;
       } else {
           managerService.setNeedBeer(true);

           throw new NeedBeerException("Not enough beer in stock!");
        }
    }



    private boolean thereIsBeer(final List<OrderItem> orders) {
        for (OrderItem part: orders) {
            final long id = part.getIdBeer();

            final Optional<BeerEntity> optionalBeerEntity = beerRepository.findById(id);

            if (optionalBeerEntity.isPresent()) {
                final BeerEntity beerEntity = optionalBeerEntity.get();

                if (part.getLiters() > beerEntity.getLitersInStock())
                    return false;

            } else return false;
        }

        return true;
    }

    private void sellBeer(final List<OrderItem> orders) {
        for (OrderItem part: orders) {
            final long id = part.getIdBeer();
            final Optional<BeerEntity> optionalBeerEntity = beerRepository.findById(id);
            final BeerEntity beerEntity = optionalBeerEntity.get();
            final int totalLitersInStock = beerEntity.getLitersInStock() - part.getLiters();
            beerEntity.setLitersInStock(totalLitersInStock);
            beerRepository.save(beerEntity);
        }
    }

    private double totalPrice(final List<OrderItem> products) {
        int totalPrice = 0;
        for (OrderItem order: products) {
            BeerEntity beerEntity = beerRepository.findById(order.getIdBeer()).get();
            totalPrice =+ order.getLiters() * beerEntity.getCostPrice();
        }

        BigDecimal bigDecimal = new BigDecimal(new BigInteger(String.valueOf(totalPrice)), 2);
        return bigDecimal.doubleValue();
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
