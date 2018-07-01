package com.aromero.theamcrmservice.api.customer;

import com.aromero.theamcrmservice.api.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerService {
    @Autowired
    private CustomerRepository customerRepository;

    public List<Customer> getAllCustomers() {
        return (List<Customer>) customerRepository.findAll();
    }

    public Optional<Customer> getCustomerById(Long id) {
        return customerRepository.findById(id);
    }

    public void saveCustomer(Customer customer, User user) {
        if (customer.getId() == null) {
            customer.setCreatedByUser(user);
        } else {
            customer.setModifiedByUser(user);
        }
        customerRepository.save(customer);
    }

    public void deleteCustomer(Long id) {
        customerRepository.deleteById(id);
    }
}
