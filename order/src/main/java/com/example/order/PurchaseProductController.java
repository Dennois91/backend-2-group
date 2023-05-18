package com.example.order;

import jakarta.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/purchaseProducts")
public class PurchaseProductController {
    private static final Logger log = LoggerFactory.getLogger(PurchaseProductController.class);
    private final PurchaseProductRepo repo;
    // private final ProductRepo productRepo; Todo
    private final PurchaseRepo purchaseRepo;

    public PurchaseProductController
            (PurchaseProductRepo repo, PurchaseRepo purchaseRepo /* ProductRepo productRepo */) {
        this.repo = repo;
        this.purchaseRepo = purchaseRepo;
        // this.productRepo = productRepo; TODO
    }

    @RequestMapping
    public List<PurchaseProduct> getPurchaseProducts() {
        return repo.findAll();
    }

    @RequestMapping("/{id}")
    public PurchaseProduct getPurchaseItemById(@PathVariable Long id) {
        return repo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(String.valueOf(id)));
    }

    @RequestMapping("/add")
    public String addPurchaseProduct(@RequestParam int quantity, /* @RequestParam Long productId */ @RequestParam Long purchaseId) {
        /* TODO
        if (productRepo.findById(productId).isPresent()) {
            if (purchaseRepo.findById(purchaseId).isPresent()) {
                Product product = productRepo.findById(productId).get();
                Purchase purchase = purchaseRepo.findById(purchaseId).get();
                repo.save(new PurchaseProduct(product.getTitle(), quantity, product.getPrice(), product, purchase));
                log.info("Product added to purchase");
                return "Product added to purchase.";
            }
            return "Purchase id not valid.";
        }
        return "Product id not valid.";
         */

        return "temp message";
    }

    @RequestMapping("/delete/{id}")
    public String deletePurchaseProductById(@PathVariable Long id) {
        if (repo.findById(id).isPresent()) {
            PurchaseProduct p = repo.findById(id).get();
            repo.deleteById(id);
            log.info("Product removed from purchase: " + p);
            return "Product removed from purchase: " + p;
        }
        return "Purchased product with id= " + id + " not found";
    }
}
