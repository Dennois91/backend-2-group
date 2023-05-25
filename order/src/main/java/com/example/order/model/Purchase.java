package com.example.order.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Purchase {

    @Id
    @GeneratedValue
    private Long id;
    private Long customerId;
    private String address;
    private String zipCode;
    private String locality;

    @Column(nullable = false, updatable = false)
    @CreationTimestamp
    private @Setter(AccessLevel.NONE) Instant dateCreated;
    @UpdateTimestamp
    private @Setter(AccessLevel.NONE) Instant dateUpdated;

    public Purchase(Long id, Long customerId, String address, String zipCode, String locality) {
        this.id = id;
        this.customerId = customerId;
        this.address = address;
        this.zipCode = zipCode;
        this.locality = locality;
    }
}