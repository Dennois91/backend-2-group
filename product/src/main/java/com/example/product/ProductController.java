package com.example.product;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.retry.annotation.Retryable;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.time.LocalDateTime;
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
    public ResponseEntity<String> handleEntityNotFoundException(Exception e) {
        log.warn("handleEntityNotFoundException: " + e.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(HttpStatus.INTERNAL_SERVER_ERROR
                + ": " + e.getMessage() + ". " + LocalDateTime.now());
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<String> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException e) {
        log.warn("MethodArgumentTypeMismatchException: " + e.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(HttpStatus.BAD_REQUEST
                + ": " + e.getMessage() + ". " + LocalDateTime.now());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<String> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        log.warn("MethodArgumentNotValidException: " + e.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(HttpStatus.BAD_REQUEST
                + ": " + e.getMessage() + ". " + LocalDateTime.now());
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<String> handleHttpMessageNotReadable(HttpMessageNotReadableException e) {
        log.warn("HttpMessageNotReadableException: " + e.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(HttpStatus.BAD_REQUEST
                + ": " + e.getMessage() + ". " + LocalDateTime.now());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleException(Exception e) {
        log.warn("Exception: " + e.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(HttpStatus.INTERNAL_SERVER_ERROR
                + ": " + e.getMessage() + ". " + LocalDateTime.now());
    }
}