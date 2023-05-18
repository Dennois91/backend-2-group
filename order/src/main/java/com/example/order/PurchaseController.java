package com.example.order;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.logging.Logger;

@RestController
@RequestMapping("/purchases")
public class PurchaseController {
    private final PurchaseRepo purchaseRepo;
    // private final CustomerRepo customerRepo; TODO
    private static final Logger LOGGER = Logger.getLogger(PurchaseController.class.getName());

    public PurchaseController(PurchaseRepo purchaseRepo /* CustomerRepo customerRepo */) {
        this.purchaseRepo = purchaseRepo;
       // this.customerRepo = customerRepo; TODO
        }

    @RequestMapping
    public List<Purchase> getAllPurchases() {
        LOGGER.info("getAllPurchases called");
        return purchaseRepo.findAll();
    }

    @RequestMapping("/{id}")
    public Purchase getPurchaseById(@PathVariable Long id) {
        LOGGER.info("getPurchaseById called");
        return purchaseRepo.findById(id).orElseThrow(()
                -> new EntityNotFoundException("Purchase with id: " + id + " not found"));
    }

    @PostMapping("/add")
    public String addPurchase(@RequestParam String address,@RequestParam String zipcode
            ,@RequestParam String locality /* @RequestParam Long customerId */) {

        /* TODO
        if (customerRepo.findById(customerId).isPresent()) {
            purchaseRepo.save(new Purchase(address, zipcode, locality, customerRepo.findById(customerId).get()));
            LOGGER.info("Purchase added");
            return "Purchase added";
        }
        return "Customer id not valid";
         */

        return "temp message";
    }

    @RequestMapping("/delete/{id}")
    public String deletePurchase(@PathVariable Long id) {
        LOGGER.warning("deletePurchase called");
        purchaseRepo.deleteById(id);
        return "Purchase deleted " + id + " Deleted";
    }
}