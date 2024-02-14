package com.smitty.spring.guru.spring6restmvc.controller;

import com.smitty.spring.guru.spring6restmvc.model.Customer;
import com.smitty.spring.guru.spring6restmvc.service.CustomerService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/customers")
@Slf4j
public class CustomerController {
    private final CustomerService customerService;

    @PostMapping("/customer")
    public ResponseEntity<Void> addANewCustomer(@RequestBody Customer customer) throws URISyntaxException {
        Customer createdCustomer = customerService.addANewCustomer(customer);
        return ResponseEntity.created(new URI("/api/v1/customers/customer/" + createdCustomer.getId())).build();
    }

    @PutMapping("/customer/{customerId}")
    public ResponseEntity<Void> updateCustomer(@PathVariable String customerId, @RequestBody Customer updatedCustomer) {
        customerService.updateCustomer(customerId, updatedCustomer);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/customer/{customerId}")
    public ResponseEntity<Void>  deleteCustomerById(@PathVariable String customerId) {
        customerService.deleteCustomer(customerId);
        return ResponseEntity.noContent().build();
    }

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
