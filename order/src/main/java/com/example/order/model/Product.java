package com.example.order.model;

import lombok.*;
import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Product {
    private Long id;
    private String title;
    private String description;
    private double price;
    private int balance;
    private Instant dateCreated;
    private Instant dateUpdated;
}