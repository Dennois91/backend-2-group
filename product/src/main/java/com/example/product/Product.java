package com.example.product;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Product {
    @Id
    @GeneratedValue
    private Long id;
    private String title;
    private String description;
    private double price;
    private int balance;
    @Column(nullable = false, updatable = false)
    @CreationTimestamp
    private @Setter(AccessLevel.NONE) Instant dateCreated;
    @UpdateTimestamp
    private @Setter(AccessLevel.NONE) Instant dateUpdated;

    public Product(String title, String description, double price, int balance) {
        this.title = title;
        this.description = description;
        this.price = price;
        this.balance = balance;
    }
    public Product(Long id, String title, String description, double price, int balance) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.price = price;
        this.balance = balance;
    }
}