package com.example.customer;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

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
    public List<Customer> getAllCustomers() {
        log.info("All customers have been returned!");
        return customerRepo.findAll();
    }

    @RequestMapping("/{id}")
    public Customer findById(@PathVariable Long id) {
        log.info("All customers matching the chosen ID have been returned!");
        return customerRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(String.valueOf(id)));
    }

    @RequestMapping("/{firstName}/firstName")
    public List<Customer> customerByFirstName(@PathVariable String firstName) {
        log.info("All customers matching the chosen firstname have been returned!");
        return customerRepo.findByFirstName(firstName);
    }

    @RequestMapping("/{lastName}/lastName")
    public List<Customer> customerByLastName(@PathVariable String lastName) {
        log.info("All customers matching the chosen lastname have been returned!");
        return customerRepo.findByLastName(lastName);
    }

    @RequestMapping("/Delete/{id}")
    public String deleteCustomerById(@PathVariable Long id) {
        customerRepo.deleteById(id);
        log.info("Customer have been deleted by matching ID!");
        return "Customer with id " + id + " have been deleted";
    }

    @PostMapping("/add")
    public String addCustomer(@Valid @RequestBody Customer customer) {
        customerRepo.save(customer);
        log.info("Customer have been added");
        return "Customer " + customer.getFirstName() + " " + customer.getLastName() + " have been added";
    }
    @RequestMapping("/addWithParams")
    public String addCustomerWithParams(@RequestParam String ssn, @RequestParam String firstName, @RequestParam String lastName,
                                        @RequestParam String phone, @RequestParam String email) {
        customerRepo.save(new Customer(ssn, firstName, lastName, phone, email));
        log.info("Customer have been added");
        return "Customer " + firstName + " " + lastName + " have been added";
    }
}