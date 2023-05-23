package com.example.order.model;

import lombok.*;
import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Customer {
    private Long id;
    private String ssn;
    private String firstName;
    private String lastName;
    private String phone;
    private String email;
    private Instant dateCreated;
    private Instant dateUpdated;
}