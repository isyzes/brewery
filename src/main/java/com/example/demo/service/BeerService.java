package com.example.demo.service;

import com.example.demo.dto.beer.Beer;
import com.example.demo.dto.beer.OrderCreatedBeer;
import com.example.demo.dto.beer.ResponseUpdatedLitersBeer;
import com.example.demo.dto.order.OrderItem;
import com.example.demo.dto.order.RequestOrder;
import com.example.demo.dto.order.ResponseOrder;
import com.example.demo.dto.recipe.RecipeItem;
import com.example.demo.entity.*;
import com.example.demo.exception.BreweryBeerException;
import com.example.demo.exception.BreweryIngredientException;
import com.example.demo.exception.BreweryUpdatedBeerException;
import com.example.demo.mapper.BeerRequestMapper;
import com.example.demo.mapper.ListBeerMapper;
import com.example.demo.mapper.ListRecipeItemMapper;
import com.example.demo.mapper.RecipeMapper;
import com.example.demo.repository.BeerRepository;
import com.example.demo.repository.RecipeRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

@AllArgsConstructor
@Service
public class BeerService {
    private final BeerRepository beerRepository;
    private final RecipeRepository recipeRepository;

    private final IngredientService warehouseService;
    private final ManagerService managerService;
    private final OrderService orderService;

    private final BeerRequestMapper beerRequestMapper;
    private final RecipeMapper recipeMapper;
    private final ListRecipeItemMapper listRecipeItemMapper;
    private final ListBeerMapper listBeerMapper;

    @Transactional
    public Beer updatedBeer(final Beer newBeer, final long idBeer) throws BreweryUpdatedBeerException {
        final Optional<BeerEntity> optionalBeer = beerRepository.findById(idBeer);

        if (optionalBeer.isPresent()) {
            final BeerEntity beerEntity = optionalBeer.get();
            updatedBeer(beerEntity, newBeer);

            return beerRequestMapper.destinationToSource(beerEntity);
        } else throw new BreweryUpdatedBeerException("Beer with id=" + idBeer + " not found!");
    }

    public List<Beer> getBeers() {
        return listBeerMapper.destinationToSource(beerRepository.findAll());
    }

    @Transactional
    public ResponseUpdatedLitersBeer updatedLitersInStockBeer(final OrderCreatedBeer orderCreatedBeer) throws BreweryIngredientException, BreweryBeerException {
        final long idBeer = orderCreatedBeer.getIdBeer();
        final int liters = orderCreatedBeer.getLiters();

        final Optional<BeerEntity> optionalBeer = beerRepository.findById(idBeer);

        if (optionalBeer.isPresent()) {
            final BeerEntity beerEntity = optionalBeer.get();
            final List<RecipeItem> recipe = listRecipeItemMapper.destinationToSource(beerEntity.getRecipe().getItems());

            final List<IngredientEntity> ingredientsOfRecipe = warehouseService.ingredientsOfRecipe(recipe, orderCreatedBeer.getLiters());

            if (!ingredientsOfRecipe.isEmpty()) {
                warehouseService.takeIngredientsForBeer(recipe, ingredientsOfRecipe);

                final int totalLitersInStock = beerEntity.getLitersInStock() + liters;

                beerEntity.setLitersInStock(totalLitersInStock);

                beerRepository.save(beerEntity);

                final ResponseUpdatedLitersBeer response = new ResponseUpdatedLitersBeer();
                response.setIdBeer(idBeer);
                response.setNameBeer(beerEntity.getName());
                response.setTotalLiters(totalLitersInStock);
                return response;
            }
            else {
                managerService.needIngredient();

                throw new BreweryIngredientException("No ingredients in stock!");
            }
        }

        throw new BreweryBeerException("No beer in the knowledge base!");
    }

    @Transactional
    public ResponseOrder createOrder(final RequestOrder requestOrder, final AuthInfoEntity authInfoEntity) throws BreweryBeerException {
        final List<BeerEntity> beersOfOrders = beersOfOrders(requestOrder.getItems());

        if (!beersOfOrders.isEmpty()) {
            updatedLitersInStock(requestOrder.getItems(), beersOfOrders);
            return orderService.createOrder(requestOrder, beersOfOrders, authInfoEntity.getUser());
        } else {
            managerService.needBeer();
            throw new BreweryBeerException("Not enough beer in stock!");
        }
    }

    private List<BeerEntity> beersOfOrders(final List<OrderItem> orders) {
        final List<BeerEntity> result = new ArrayList<>();

        final AtomicInteger liters = new AtomicInteger();

        orders.stream()
                .peek(part ->liters.set(part.getLiters()))
                .map(part -> beerRepository.findById(part.getBeer().getId()))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .filter(beer -> !(beer.getLitersInStock() < (liters.get())))
                .forEach(result::add);
        return result;
    }

    private void updatedLitersInStock(final List<OrderItem> orders, final List<BeerEntity> beerEntities) {
        for (OrderItem part: orders) {
            final long id = part.getBeer().getId();
            final BeerEntity beer = getBeer(id, beerEntities);
            final int totalLitersInStock = beer.getLitersInStock() - part.getLiters();
            beer.setLitersInStock(totalLitersInStock);
            beerRepository.save(beer);
        }
    }

    private BeerEntity getBeer(final long id, List<BeerEntity> beerEntities) {
        return beerEntities.stream()
                .filter(beer -> beer.getId() == id)
                .findFirst()
                .get();
    }

    private void updatedBeer(final BeerEntity beerEntity, final Beer newBeer) {
        if (newBeer.getName() != null) beerEntity.setName(newBeer.getName());

        if (newBeer.getColor() != null) beerEntity.setColor(newBeer.getColor());

        if (newBeer.getFortress() > 0.0) beerEntity.setFortress(newBeer.getFortress());

        if (newBeer.getShelfLife() > 0) beerEntity.setShelfLife(newBeer.getShelfLife());

        if (newBeer.getCostPrice() > 0) beerEntity.setCostPrice(newBeer.getCostPrice());

        if (newBeer.getRecipe() != null) {
            final RecipeEntity recipeEntity = recipeMapper.sourceToDestination(newBeer.getRecipe());
            recipeRepository.save(recipeEntity);
            beerEntity.setRecipe(recipeEntity);
        }
        beerRepository.save(beerEntity);
    }
}
