package com.example.product;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.retry.annotation.Retryable;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;


import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {
    private static final Logger log = LoggerFactory.getLogger(ProductController.class);
    private final ProductRepo repo;

    public ProductController(ProductRepo repo) {
        this.repo = repo;
    }

    @Retryable
    @RequestMapping
    public List<Product> getAllProducts() {
        return repo.findAll();
    }

    @Retryable
    @RequestMapping("/{id}")
    public Product findById(@PathVariable Long id) {
        log.info("All products matching the chosen ID have been returned!");
        return repo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(String.valueOf(id)));
    }

    @Retryable
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

    @Retryable
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

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<String> handleEntityNotFoundException(EntityNotFoundException e){
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error: " + HttpStatus.INTERNAL_SERVER_ERROR
                + " - Product with the specified ID does not exist!" + " Id used = " + e.getMessage());
    }
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<String> handleMethodNotSupportedException(HttpRequestMethodNotSupportedException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid request. Please check the URL.");
    }
    @ExceptionHandler(HttpClientErrorException.BadRequest.class)
    public ResponseEntity<String> handleBadRequestException(HttpClientErrorException.BadRequest e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("BadRequest" + e.getMessage());
    }
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<String> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid request. Please check the URL.");
    }
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<String> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid request. Please check the URL.");
    }
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleException(Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred.");
    }
}