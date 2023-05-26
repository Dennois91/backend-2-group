package com.example.product;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
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

    @Size(min = 1, max = 30)
    @NotBlank
    private String title;

    @Size(min = 1, max = 200)
    @NotBlank
    private String description;

    @Min(0)
    @Max(1000000)
    private double price;

    @Min(0)
    @Max(9999999)
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