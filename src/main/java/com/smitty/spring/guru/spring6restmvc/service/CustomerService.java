package com.smitty.spring.guru.spring6restmvc.service;

import com.smitty.spring.guru.spring6restmvc.model.Customer;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Service
@Slf4j
public class CustomerService {
    private static final Map<UUID, Customer> CUSTOMER_MAP = new LinkedHashMap<>();

    static {
        Customer customerOne = Customer.builder()
                .id(UUID.randomUUID())
                .customerName("Willy Wonka")
                .version(1)
                .createdDate(LocalDateTime.now())
                .lastModifiedDate(LocalDateTime.now())
                .build();

        Customer customerTwo = Customer.builder()
                .id(UUID.randomUUID())
                .customerName("Curly Howard")
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

    public Customer addANewCustomer(Customer customer) {
        Customer newCustomer = Customer.builder()
                .id(UUID.randomUUID())
                .customerName(customer.getCustomerName())
                .version(1)
                .createdDate(LocalDateTime.now())
                .lastModifiedDate(LocalDateTime.now())
                .build();
        CUSTOMER_MAP.put(newCustomer.getId(), newCustomer);
        return newCustomer;
    }

    public Customer updateCustomer(String customerId, Customer updatedCustomer) {
        Customer customerToUpdate = CUSTOMER_MAP.get(UUID.fromString(customerId));
        Customer newUpdatedCustomer = Customer.builder()
                .id(customerToUpdate.getId())
                .customerName(updatedCustomer.getCustomerName())
                .version(updatedCustomer.getVersion())
                .lastModifiedDate(LocalDateTime.now())
                .createdDate(customerToUpdate.getCreatedDate())
                .build();
        CUSTOMER_MAP.put(newUpdatedCustomer.getId(), newUpdatedCustomer);
        return newUpdatedCustomer;

    }

    public void deleteCustomer(String customerId) {
        CUSTOMER_MAP.remove(UUID.fromString(customerId));
    }

    public void patchCustomer(String customerId, Customer customerToPatch) {
        Customer customer = CUSTOMER_MAP.get(UUID.fromString(customerId));
        new ModelMapper().map(customerToPatch, customer);
        CUSTOMER_MAP.put(customer.getId(), customer);
    }
}
