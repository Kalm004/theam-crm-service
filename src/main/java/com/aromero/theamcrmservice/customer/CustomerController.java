package com.aromero.theamcrmservice.customer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

@RestController
public class CustomerController {
    @Autowired
    private CustomerService customerService;

    @GetMapping("/customer")
    public List<Customer> getAll() {
        return customerService.getAllCustomers();
    }

    @GetMapping("/customer/{id}")
    public Customer getById(@PathVariable(value = "id") Long id) {
        Optional<Customer> customer = customerService.getCustomerById(id);
        if (customer.isPresent()) {
            return customer.get();
        } else {
            throw new EntityNotFoundException("Customer with id " + id + " not found");
        }
    }

    @PostMapping("/customer")
    public void createCustomer(@RequestBody Customer customer) {
        customerService.saveCustomer(customer);
    }

    @PutMapping("/customer/{id}")
    public void updateCustomer(@PathVariable(value = "id") Long id, @RequestBody Customer customer) {
        customerService.saveCustomer(customer);
    }

    @DeleteMapping("/customer/{id}")
    public void deleteCustomer(@PathVariable(value = "id") Long id) {
        customerService.deleteCustomer(id);
    }
}
