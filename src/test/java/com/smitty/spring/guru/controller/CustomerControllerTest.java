package com.smitty.spring.guru.controller;

import com.smitty.spring.guru.spring6restmvc.controller.CustomerController;
import com.smitty.spring.guru.spring6restmvc.model.Customer;
import com.smitty.spring.guru.spring6restmvc.service.CustomerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.UUID;

import static java.lang.String.join;
import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringJUnitConfig(classes = {CustomerService.class, CustomerController.class})
class CustomerControllerTest {
    private MockMvc mockMvc;
    private Customer testCustomer;
    private UUID randomUID;

    @Autowired
    private CustomerService customerService;

    @Autowired
    CustomerController customerController;

    @BeforeEach
    void setUp() {
        testCustomer = customerService.listCustomers().get(0);
        randomUID = testCustomer.getId();
        mockMvc = MockMvcBuilders.standaloneSetup(customerController).build();
    }

    @Test
    void getAllCustomersTest() throws Exception {
        mockMvc.perform(get("/api/v1/customers")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()", is(2)));
    }

    @Test
    void getCustomerById() throws Exception {
        mockMvc.perform(get(join("", "/api/v1/customers/customer/", randomUID.toString()))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(randomUID.toString())))
                .andExpect(jsonPath("$.customerName", is(testCustomer.getCustomerName())))
                .andExpect(jsonPath("$.version", is(testCustomer.getVersion())));
    }

}
