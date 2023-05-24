package com.example.order.controller;

import com.example.order.model.Product;
import com.example.order.repository.PurchaseProductRepo;
import com.example.order.repository.PurchaseRepo;
import com.example.order.model.PurchaseProduct;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.logging.Logger;

@RestController
@RequestMapping("/purchaseProducts")
public class PurchaseProductController {
    private static final java.util.logging.Logger LOGGER = Logger.getLogger(PurchaseController.class.getName());
    private final PurchaseProductRepo repo;
    private final PurchaseRepo purchaseRepo;

    @Autowired
    private RestTemplate restTemplate;

    public PurchaseProductController
            (PurchaseProductRepo repo, PurchaseRepo purchaseRepo) {
        this.repo = repo;
        this.purchaseRepo = purchaseRepo;
    }

    @RequestMapping
    public List<PurchaseProduct> getPurchaseProducts() {
        LOGGER.info("getPurchaseProducts called");
        return repo.findAll();
    }

    @RequestMapping("/{id}")
    public PurchaseProduct getPurchaseItemById(@PathVariable Long id) {
        LOGGER.info("getPurchaseProductById: " + id + " called");
        return repo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Purchased product id: " + id + " not valid"));
    }

//    @PostMapping("/add")
//    public String addPurchaseProduct(@RequestBody PurchaseProduct purchaseProduct) {
//
//        try {
//            Product product = restTemplate.getForObject(
//                    "http://localhost:8080/products/" + purchaseProduct.getProductId(), Product.class);
//            if (product != null) {
//                if (purchaseRepo.findById(purchaseProduct.getPurchaseId()).isPresent()) {
//                    repo.save(new PurchaseProduct(purchaseProduct.getId(), purchaseProduct.getProductId(),
//                            purchaseProduct.getPurchaseId(), product.getTitle(),
//                            purchaseProduct.getQuantity(), product.getPrice()));
//                    if (repo.findById(purchaseProduct.getId()).isPresent()) {
//                        LOGGER.info("Updated purchased product id: " + purchaseProduct.getId());
//                        return "Purchased product updated";
//                    } else {
//                        LOGGER.info("Created purchased product to customer id: " + purchaseProduct.getPurchaseId());
//                        return "Purchased product created";
//                    }
//                }
//                return "Purchase id not valid";
//            }
//        } catch (Exception e) {
//            LOGGER.warning(e.toString());
//        }
//        return "Product id not valid";
//    }

    @RequestMapping("/delete/{id}")
    public String deletePurchaseProduct(@PathVariable Long id) {
        if (repo.findById(id).isPresent()) {
            repo.deleteById(id);
            LOGGER.warning("Deleted purchaseProduct id: " + id);
            return "Purchased product with id: " + id + ", deleted";
        }
        return "Purchased product id: " + id + " not valid";
    }
}