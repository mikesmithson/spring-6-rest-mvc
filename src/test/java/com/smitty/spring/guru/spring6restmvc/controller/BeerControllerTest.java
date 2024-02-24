package com.smitty.spring.guru.spring6restmvc.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.smitty.spring.guru.spring6restmvc.model.Beer;
import com.smitty.spring.guru.spring6restmvc.model.BeerStyle;
import com.smitty.spring.guru.spring6restmvc.service.BeerService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static java.lang.String.join;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.closeTo;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(BeerController.class)
public class BeerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private BeerService beerService;

    private final Beer testBeer = Beer.builder()
            .id(UUID.randomUUID())
            .version(1)
            .beerName("Galaxy Cat")
            .beerStyle(BeerStyle.PALE_ALE)
            .upc("12356")
            .price(new BigDecimal("12.99"))
            .quantityOnHand(122)
            .createdDate(LocalDateTime.now())
            .updateDate(LocalDateTime.now())
            .build();
    private final List<Beer> testBeers = List.of(testBeer);
    private UUID randomUID = testBeer.getId();

    @Test
    void createABeer() throws Exception {
        Beer newBeer = Beer.builder()
                .id(UUID.randomUUID())
                .beerStyle(BeerStyle.STOUT)
                .beerName("Detroit Liquid Old Head Milk Chocolate Stout")
                .upc("7654321")
                .price(BigDecimal.valueOf(9.99))
                .version(1)
                .quantityOnHand(12)
                .createdDate(LocalDateTime.now())
                .updateDate(LocalDateTime.now())
                .build();

        given(beerService.createABeer(newBeer)).willReturn(newBeer);

        mockMvc.perform(post("/api/v1/beers")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(newBeer)))
                .andExpect(status().isCreated())
                .andExpect(header().exists("Location"));
    }

    @Test
    void getAllBeersTest() throws Exception {
        given(beerService.listBeers()).willReturn(testBeers);
        mockMvc.perform(get("/api/v1/beers")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()", is(1)));
    }

    @Test
    void getBeerById() throws Exception {
        given(beerService.getBeerById(randomUID)).willReturn(testBeer);
        mockMvc.perform(get(join("", "/api/v1/beers/beer/", randomUID.toString()))
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
