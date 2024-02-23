package com.smitty.spring.guru.controller;

import com.smitty.spring.guru.spring6restmvc.controller.BeerController;
import com.smitty.spring.guru.spring6restmvc.model.Beer;
import com.smitty.spring.guru.spring6restmvc.service.BeerService;
import static org.hamcrest.CoreMatchers.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.math.BigDecimal;
import java.util.UUID;

import static java.lang.String.join;
import static org.hamcrest.Matchers.closeTo;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class BeerControllerTest {
    private MockMvc mockMvc;
    private Beer testBeer;
    private UUID randomUID;


    @BeforeEach
    void setUp() {
        BeerService beerService = new BeerService();
        testBeer = beerService.listBeers().get(0);
        randomUID = testBeer.getId();
        mockMvc = MockMvcBuilders.standaloneSetup(new BeerController(beerService)).build();
    }

    @Test
    void getBeerById() throws Exception {
        mockMvc.perform(get(join("","/api/v1/beers/beer/", randomUID.toString()))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(randomUID.toString())))
                .andExpect(jsonPath("$.version", is(testBeer.getVersion())))
                .andExpect(jsonPath("$.beerName", is(testBeer.getBeerName())))
                .andExpect(jsonPath("$.beerStyle", is(testBeer.getBeerStyle().name())))
                .andExpect(jsonPath("$.upc", is(testBeer.getUpc())))
                .andExpect(jsonPath("$.quantityOnHand", is(testBeer.getQuantityOnHand())))
                .andExpect(jsonPath("$.price", closeTo(testBeer.getPrice().doubleValue(), .0001)));
    }

}
