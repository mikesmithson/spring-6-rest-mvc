package com.smitty.spring.guru.spring6restmvc.model;

import lombok.Builder;
import lombok.Data;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
public class Customer {
    private final UUID id;
    @Setter
    private String customerName;
    @Setter
    private Integer version;
    private final LocalDateTime createdDate;
    private final LocalDateTime lastModifiedDate;
}
