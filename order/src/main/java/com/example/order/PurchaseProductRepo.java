package com.example.order;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PurchaseProductRepo extends JpaRepository<PurchaseProduct, Long> {
    List<PurchaseProduct> findByPurchase(Purchase purchase);
}

