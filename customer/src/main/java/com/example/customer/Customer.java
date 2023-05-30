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
    @Size(min = 1, max = 12)
    @NotBlank
    private String ssn;
    @Pattern(regexp = "^[a-zA-Z\s]{1,20}$")
    @NotBlank
    private String firstName;
    @Pattern(regexp = "^[a-zA-Z\s]{1,20}$")
    @NotBlank
    private String lastName;
    @Size(min = 1, max = 12)
    @NotBlank
    private String phone;
    @Size(min = 1, max = 30)
    @Email
    @NotBlank
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

}
