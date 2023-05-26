package com.example.customer;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Customer {

    @Id
    @GeneratedValue
    private Long id;
    @Min(0) @Max(12) @NotBlank
    private String ssn;
    @Min(0) @Max(20) @NotBlank @Pattern(regexp = "^[a-zA-Z]+$")
    private String firstName;
    @Min(0) @Max(20) @NotBlank @Pattern(regexp = "^[a-zA-Z]+$")
    private String lastName;
    @Min(0) @Max(12) @NotBlank
    private String phone;
    @Min(0) @Max(20) @NotBlank @Email
    private String email;

    @Column(nullable = false, updatable = false)
    @CreationTimestamp
    private @Setter(AccessLevel.NONE) Instant dateCreated ;
    @UpdateTimestamp
    private @Setter(AccessLevel.NONE) Instant dateUpdated ;

    public Customer(String ssn, String firstName, String lastName, String phone, String email) {
        this.ssn = ssn;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
        this.email = email;
    }

    public Customer(Long id, String ssn, String firstName, String lastName, String phone, String email) {
        this.id = id;
        this.ssn = ssn;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
        this.email = email;
    }

    public Customer(String firstName, String lastName, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }

}
