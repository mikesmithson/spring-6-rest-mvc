package com.smitty.spring.guru.controller;

import com.smitty.spring.guru.spring6restmvc.controller.BeerController;
import com.smitty.spring.guru.spring6restmvc.service.BeerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class BeerControllerTest {
    private MockMvc mockMvc;

    @Mock
    BeerService beerService;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(new BeerController(beerService)).build();
    }
    @Test
    void getBeerById() throws Exception {
        mockMvc.perform(get("/api/v1/beers/beer/" + UUID.randomUUID())
                        .accept(MediaType.APPLICATION_JSON))
                        .andExpect(status().isOk());
    }

}
