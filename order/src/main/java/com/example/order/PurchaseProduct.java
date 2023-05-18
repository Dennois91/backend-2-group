package com.example.order;

import jakarta.persistence.*;
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
    private String title;
    private int quantity;
    private double price;

    @Column(nullable = false, updatable = false)
    @CreationTimestamp
    private @Setter(AccessLevel.NONE) Instant dateCreated;
    @UpdateTimestamp
    private @Setter(AccessLevel.NONE) Instant dateUpdated;

    /* TODO
    @ManyToOne(optional = false)
    @JoinColumn
    private Product product;
     */

    @ManyToOne(optional = false)
    @JoinColumn
    private Purchase purchase;

    public PurchaseProduct(String title, int quantity, double price, /* Product product, */ Purchase purchase) {
        this.title = title;
        this.quantity = quantity;
        this.price = price;
        // this.product = product; TODO
        this.purchase = purchase;
    }

    public PurchaseProduct(Long id, String title, int quantity, double price,  /* Product product, */ Purchase purchase) {
        this.id = id;
        this.title = title;
        this.quantity = quantity;
        this.price = price;
        // this.product = product; TODO
        this.purchase = purchase;
    }
}