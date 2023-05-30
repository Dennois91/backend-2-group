package com.example.customer;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.retry.annotation.Retryable;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/customers")
public class CustomerController {

    private final CustomerRepo customerRepo;
    private static final Logger log = LoggerFactory.getLogger(CustomerController.class);

    public CustomerController(CustomerRepo customerRepo) {
        this.customerRepo = customerRepo;
    }


    @RequestMapping
    @Retryable
    public List<Customer> getAllCustomers() {
        log.info("All customers have been returned!");
        return customerRepo.findAll();
    }

    @RequestMapping("/{id}")
    @Retryable
    public Customer findById(@PathVariable Long id) {
        log.info("All customers matching the chosen ID have been returned!");
        return customerRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(String.valueOf(id)));
    }
    @RequestMapping("/{firstName}/firstName")
    @Retryable
    public List<Customer> customerByFirstName(@PathVariable String firstName) {
        log.info("All customers matching the chosen firstname have been returned!");
        return customerRepo.findByFirstName(firstName);
    }

    @RequestMapping("/{lastName}/lastName")
    @Retryable
    public List<Customer> customerByLastName(@PathVariable String lastName) {
        log.info("All customers matching the chosen lastname have been returned!");
        return customerRepo.findByLastName(lastName);
    }

    @RequestMapping("/Delete/{id}")
    @Retryable
    public String deleteCustomerById(@PathVariable Long id) {
        customerRepo.deleteById(id);
        log.info("Customer have been deleted by matching ID!");
        return "Customer with id " + id + " have been deleted";
    }

    @PostMapping("/add")
    @Retryable
    public String addCustomer(@Valid @RequestBody Customer customer) {
        customerRepo.save(customer);
        log.info("Customer have been added");
        return "Customer " + customer.getFirstName() + " " + customer.getLastName() + " have been added";
    }
    @RequestMapping("/addWithParams")
    @Retryable
    public String addCustomerWithParams(@RequestParam String ssn, @RequestParam String firstName, @RequestParam String lastName,
                                        @RequestParam String phone, @RequestParam String email) {
        customerRepo.save(new Customer(ssn, firstName, lastName, phone, email));
        log.info("Customer have been added");
        return "Customer " + firstName + " " + lastName + " have been added";
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<String> handleEntityNotFoundException(Exception e){
        log.warn("handleEntityNotFoundException: " + e.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(HttpStatus.INTERNAL_SERVER_ERROR
                + ": " + e.getMessage() + ". " + LocalDateTime.now());
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<String> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException e) {
        log.warn("MethodArgumentTypeMismatchException: " + e.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(HttpStatus.BAD_REQUEST
                + ": " +  e.getMessage() + ". " + LocalDateTime.now());
    }
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<String> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        log.warn("MethodArgumentNotValidException: " + e.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(HttpStatus.BAD_REQUEST
                + ": " +  e.getMessage() + ". " + LocalDateTime.now());
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<String> handleHttpMessageNotReadable(HttpMessageNotReadableException e){
        log.warn("HttpMessageNotReadableException: " + e.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(HttpStatus.BAD_REQUEST
                + ": " +  e.getMessage() + ". " + LocalDateTime.now());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleException(Exception e) {
        log.warn("Exception: " + e.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(HttpStatus.INTERNAL_SERVER_ERROR
                + ": " +  e.getMessage() + ". " + LocalDateTime.now());
    }


}