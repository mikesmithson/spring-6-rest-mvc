package com.smitty.spring.guru.spring6restmvc.model;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
public class Customer {
    private final UUID id;
    private final String customerName;
    private final Integer version;
    private final LocalDateTime createdDate;
    private final LocalDateTime lastModifiedDate;
}
