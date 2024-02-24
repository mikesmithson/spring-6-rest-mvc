package com.smitty.spring.guru.spring6restmvc.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.smitty.spring.guru.spring6restmvc.model.Customer;
import com.smitty.spring.guru.spring6restmvc.service.CustomerService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static java.lang.String.join;
import static org.hamcrest.CoreMatchers.is;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CustomerController.class)
public class CustomerControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private CustomerService customerService;

    private final Customer testCustomer = Customer.builder()
            .id(UUID.randomUUID())
            .customerName("Curly Howard")
            .version(1)
            .createdDate(LocalDateTime.now())
            .lastModifiedDate(LocalDateTime.now())
            .build();
    private final List<Customer> testCustomers = List.of(testCustomer);

    private UUID randomUID = testCustomer.getId();

    @Test
    void createACustomer() throws Exception {
        Customer newCustomer = Customer.builder()
                .id(UUID.randomUUID())
                .customerName("Moe Howard")
                .version(1)
                .createdDate(LocalDateTime.now())
                .lastModifiedDate(LocalDateTime.now())
                .build();

        given(customerService.addANewCustomer(newCustomer)).willReturn(newCustomer);

        mockMvc.perform(post("/api/v1/customers/customer")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(newCustomer)))
                .andExpect(status().isCreated())
                .andExpect(header().exists("Location"));
    }

    @Test
    void getAllCustomersTest() throws Exception {
        given(customerService.listCustomers()).willReturn(testCustomers);

        mockMvc.perform(get("/api/v1/customers")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()", is(1)));
    }

    @Test
    void getCustomerById() throws Exception {
        given(customerService.getCustomerById(randomUID)).willReturn(testCustomer);

        mockMvc.perform(get(join("", "/api/v1/customers/customer/", randomUID.toString()))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(randomUID.toString())))
                .andExpect(jsonPath("$.customerName", is(testCustomer.getCustomerName())))
                .andExpect(jsonPath("$.version", is(testCustomer.getVersion())));
    }
}
