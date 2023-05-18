package com.example.order;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PurchaseRepo extends JpaRepository<Purchase,Long> {
   // List<Purchase> findByCustomer(Customer customer); Todo
}
