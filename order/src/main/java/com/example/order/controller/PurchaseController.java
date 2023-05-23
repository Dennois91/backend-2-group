package com.example.order.controller;

import com.example.order.model.Customer;
import com.example.order.repository.PurchaseRepo;
import com.example.order.model.Purchase;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.logging.Logger;

@RestController
@RequestMapping("/purchases")
public class PurchaseController {
    private final PurchaseRepo repo;
    private static final Logger LOGGER = Logger.getLogger(PurchaseController.class.getName());

    @Autowired
    private RestTemplate restTemplate;

    public PurchaseController(PurchaseRepo purchaseRepo) {
        this.repo = purchaseRepo;
    }

    @RequestMapping
    public List<Purchase> getPurchases() {
        LOGGER.info("getPurchases called");
        return repo.findAll();
    }

    @RequestMapping("/{id}")
    public Purchase getPurchaseById(@PathVariable Long id) {
        LOGGER.info("getPurchaseById: " + id + " called");
        return repo.findById(id).orElseThrow(()
                -> new EntityNotFoundException("Purchase id: " + id + " not valid"));
    }

    @PostMapping("/add")
    public String addPurchase(@RequestBody Purchase purchase) {
        Customer customer = restTemplate.getForObject(
                "http://localhost:8002/customers/" + purchase.getCustomerId(), Customer.class);
        if (customer != null) {
            repo.save(purchase);
            if (repo.findById(purchase.getId()).isPresent()) {
                LOGGER.info("Updated purchase id: " + purchase.getId());
                return "Purchase updated";
            } else {
                LOGGER.info("Created purchase to customer id: " + purchase.getCustomerId());
                return "Purchase created";
            }
        }
        return "Customer id not valid";
    }

    @RequestMapping("/delete/{id}")
    public String deletePurchase(@PathVariable Long id) {
        if (repo.findById(id).isPresent()) {
            repo.deleteById(id);
            LOGGER.warning("Deleted purchase id: " + id);
            return "Purchase with id: " + id + ", deleted";
        }
        return "Purchase id: " + id + " not valid";
    }
}