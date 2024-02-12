package com.smitty.spring.guru.spring6restmvc.controller;

import com.smitty.spring.guru.spring6restmvc.model.Beer;
import com.smitty.spring.guru.spring6restmvc.model.Customer;
import com.smitty.spring.guru.spring6restmvc.service.CustomerService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/customers")
@Slf4j
public class CustomerController {
    private final CustomerService customerService;

    @GetMapping("/customer/{id}")
    public Customer getById(@PathVariable("id") UUID id) {
        log.debug("Inside the getById controller {}", id.toString());
        return customerService.getCustomerById(id);
    }

    @GetMapping
    public List<Customer> listCustomers() {
        return customerService.listCustomers();
    }
}
