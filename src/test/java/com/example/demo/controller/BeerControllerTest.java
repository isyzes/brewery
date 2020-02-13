package com.example.demo.controller;

import com.example.demo.entity.BeerEntity;
import com.example.demo.entity.WarehouseEntity;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static com.example.demo.security.Roles.EMPLOYEE;
import static com.example.demo.security.Roles.MANAGER;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class BeerControllerTest extends AbstractControllerTest {

    @Test
    public void testGetBeerListIsOk() throws Exception {
        final String token = signIn(MANAGER);

//        given(beerRepository.findAll()).willReturn(getBeerEntityList());

        mockMvc.perform(get("/beers/list").header("Authorization", token)
                .contentType(MediaType.APPLICATION_JSON))

                .andExpect(status().isOk())
                .andExpect(content().json("[" +
                        "{" +
                        "\"id\":1," +
                        "\"name\":\"Garage\"," +
                        "\"color\":\"bright\"," +
                        "\"fortress\":12.5," +
                        "\"dateManufacture\":\"12.12.2020\"," +
                        "\"shelfLife\":25," +
                        "\"costPrice\":574," +
                        "\"recipe\":null" +
                        "}," +
                        "{" +
                        "\"id\":2," +
                        "\"name\":\"Miller\"," +
                        "\"color\":\"bright\"," +
                        "\"fortress\":12.5," +
                        "\"dateManufacture\":\"12.12.2020\"," +
                        "\"shelfLife\":25," +
                        "\"costPrice\":755," +
                        "\"recipe\":null" +
                        "}," +
                        "{" +
                        "\"id\":3," +
                        "\"name\":\"Heineken\"," +
                        "\"color\":\"dark\"," +
                        "\"fortress\":9.5," +
                        "\"dateManufacture\":\"12.12.2020\"," +
                        "\"shelfLife\":35," +
                        "\"costPrice\":5756," +
                        "\"recipe\":null" +
                        "}]"));
    }

    @Test
    public void testGetBeerListIsEmpty() throws Exception {
        final String token = signIn(MANAGER);

//        given(beerRepository.findAll()).willReturn(List.of());

        mockMvc.perform(get("/beers/list").header("Authorization", token)
                .contentType(MediaType.APPLICATION_JSON))

                .andExpect(status().isOk())
                .andExpect(content().json("[]"));
    }

    @Test
    public void testChangeInformationBeerIsOk() throws Exception {
        final String token = signIn(MANAGER);

        long id = 2;
        BeerEntity newBeer = new BeerEntity();
        newBeer.setId(id);
        newBeer.setColor("bright");
        newBeer.setFortress(12.5);
        newBeer.setDateManufacture(LocalDate.of(2020, 12, 12));
        newBeer.setShelfLife(25);

//        given(beerRepository.findById(id)).willReturn(Optional.of(newBeer));

        mockMvc.perform(put("/beers/change/2").header("Authorization", token)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\" : \"Grimbergen\", \"costPrice\" : 2551}"))

                .andExpect(status().isOk())
                .andExpect(content().json("{" +
                        "\"id\" : 2, " +
                        "\"name\" : \"Grimbergen\" , " +
                        "\"color\" : \"bright\", " +
                        "\"fortress\" : 12.5, " +
                        "\"dateManufacture\": \"12.12.2020\", " +
                        "\"shelfLife\": 25, " +
                        "\"costPrice\": 2551}"));
    }

    @Test
    public void testMakeBeerIsNotFound() throws Exception {
        final String token = signIn(EMPLOYEE);

        mockMvc.perform(post("/beers/make/87?quantity=21").header("Authorization", token)
                .contentType(MediaType.APPLICATION_JSON))

                .andExpect(status().isNotFound());
    }

    @Test
    public void testMakeBeerIsOk() throws Exception {
        final String token = signIn(EMPLOYEE);

        WarehouseEntity warehouseEntity = new WarehouseEntity();
        warehouseEntity.setId(1L);
        warehouseEntity.setFinishedBeerEntitiesList(new ArrayList<>());

//        given(warehouseRepository.findAll()).willReturn(List.of(warehouseEntity));

        mockMvc.perform(post("/beers/make/87/quantity=80").header("Authorization", token).contentType(MediaType.APPLICATION_JSON))

                .andExpect(status().isCreated());
    }

    private List<BeerEntity> getBeerEntityList() {
        BeerEntity first = new BeerEntity();
        first.setId(1);
        first.setName("Garage");
        first.setColor("bright");
        first.setFortress(12.5);
        first.setDateManufacture(LocalDate.of(2020, 12, 12));
        first.setShelfLife(25);
        first.setCostPrice(574);

        BeerEntity second = new BeerEntity();
        second.setId(2);
        second.setName("Miller");
        second.setColor("bright");
        second.setFortress(12.5);
        second.setDateManufacture(LocalDate.of(2020, 12, 12));
        second.setShelfLife(25);
        second.setCostPrice(755);

        BeerEntity third = new BeerEntity();
        third.setId(3);
        third.setName("Heineken");
        third.setColor("dark");
        third.setFortress(9.5);
        third.setDateManufacture(LocalDate.of(2020, 12, 12));
        third.setShelfLife(35);
        third.setCostPrice(5756);
        System.out.println();

        return List.of(first, second, third);
    }
}
