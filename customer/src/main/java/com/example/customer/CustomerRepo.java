package com.example.customer;

import org.springframework.data.jpa.repository.JpaRepository;


import java.util.List;

public interface CustomerRepo extends JpaRepository<Customer, Long> {
    List<Customer> findByFirstName(String firstName);
    List<Customer> findByLastName(String lastName);
}
