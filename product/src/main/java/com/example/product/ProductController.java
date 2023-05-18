package com.example.product;

import jakarta.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    public Product getProduct(@PathVariable("id") Long id) {
        return repo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(String.valueOf(id)));
    }

    @PostMapping("/add")
    public String addProduct(@RequestBody Product p) {
        String s;
        if (p.getId() != null && repo.existsById(p.getId())) {
            s = "Updated: ";
        } else {
            s = "Added: ";
        }
        repo.save(p);
        log.info(s + p);
        return s + p;
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