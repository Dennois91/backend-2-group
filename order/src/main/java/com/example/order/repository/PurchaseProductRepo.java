package com.example.order.repository;

import com.example.order.model.PurchaseProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PurchaseProductRepo extends JpaRepository<PurchaseProduct, Long> {
}

