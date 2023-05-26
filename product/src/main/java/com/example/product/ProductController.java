package com.example.product;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {
    private static final Logger log = LoggerFactory.getLogger(ProductController.class);
    private final ProductRepo repo;

    public ProductController(ProductRepo repo) {
        this.repo = repo;
    }

    @RequestMapping
    public List<Product> getAllProducts() {
        return repo.findAll();
    }

    @RequestMapping("/{id}")
    public Product findById(@PathVariable Long id) {
        log.info("All products matching the chosen ID have been returned!");
        return repo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(String.valueOf(id)));
    }

    @PostMapping("/add")
    public ResponseEntity<String> addProduct(@Valid @RequestBody Product p) {
        String send;
        if (p.getId() != null && repo.existsById(p.getId())) {
            send = "Updated: ";
        } else {
            send = "Added: ";
        }

        try {
            repo.save(p);
            log.info(send + p);
            return ResponseEntity.ok(send + p);
        } catch (Exception e) {

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error: " + e.getMessage());
        }
    }

    @RequestMapping("/delete/{id}")
    public String deleteProductById(@PathVariable("id") Long id) {
        if (repo.findById(id).isPresent()) {
            Product p = repo.findById(id).get();
            repo.deleteById(id);
            log.info("Deleted: " + p);
            return "Deleted: " + p;
        }
        return "Product with id= " + id + " not found";
    }
}