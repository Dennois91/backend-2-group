package com.example.order.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class PurchaseProduct {
    @Id @GeneratedValue
    private Long id;
    private Long productId;
    private Long purchaseId;
    private String title;
    @Min(1) @Max(9999999)
    private int quantity;
    private double price;

    @Column(nullable = false, updatable = false)
    @CreationTimestamp
    private @Setter(AccessLevel.NONE) Instant dateCreated;
    @UpdateTimestamp
    private @Setter(AccessLevel.NONE) Instant dateUpdated;

    public PurchaseProduct(Long id, Long productId, Long purchaseId, String title, int quantity, double price) {
        this.id = id;
        this.productId = productId;
        this.purchaseId = purchaseId;
        this.title = title;
        this.quantity = quantity;
        this.price = price;
    }
}