package com.smitty.spring.guru.spring6restmvc.service;

import com.smitty.spring.guru.spring6restmvc.model.Beer;
import com.smitty.spring.guru.spring6restmvc.model.BeerStyle;
import com.smitty.spring.guru.spring6restmvc.model.Customer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

@Service
@Slf4j
public class CustomerService {
    private static final Map<UUID, Customer> CUSTOMER_MAP = new LinkedHashMap<>();

    static {
        Customer customerOne = Customer.builder()
                .customerName("Willy Wonka")
                .id(UUID.randomUUID())
                .version(1)
                .createdDate(LocalDateTime.now())
                .lastModifiedDate(LocalDateTime.now())
                .build();

        Customer customerTwo = Customer.builder()
                .customerName("Curly Howard")
                .id(UUID.randomUUID())
                .version(1)
                .createdDate(LocalDateTime.now())
                .lastModifiedDate(LocalDateTime.now())
                .build();

        Arrays.asList(customerOne, customerTwo).forEach(customer
                -> CUSTOMER_MAP.put(customer.getId(), customer));
    }

    public List<Customer> listCustomers() {
        return new ArrayList<>(CUSTOMER_MAP.values());
    }

    public Customer getCustomerById(UUID id) {
        log.debug("Inside the Customer Service getting customer by id {}", id);
        return CUSTOMER_MAP.get(id);
    }
}
