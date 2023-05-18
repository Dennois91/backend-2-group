package com.example.order;

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
    private String address;
    private String zipCode;
    private String locality;

    @Column(nullable = false, updatable = false)
    @CreationTimestamp
    private @Setter(AccessLevel.NONE) Instant dateCreated;
    @UpdateTimestamp
    private @Setter(AccessLevel.NONE) Instant dateUpdated;

    /* TODO
    @ManyToOne(optional = false)
    @JoinColumn
    private Customer customer;
     */

    public Purchase(String address, String zipCode, String locality /*Customer customer */) {
        this.address = address;
        this.zipCode = zipCode;
        this.locality = locality;
       // this.customer = customer; TODO
    }

    public Purchase(Long id, String address, String zipCode, String locality /*Customer customer */) {
        this.id = id;
        this.address = address;
        this.zipCode = zipCode;
        this.locality = locality;
        // this.customer = customer; TODO
    }
}