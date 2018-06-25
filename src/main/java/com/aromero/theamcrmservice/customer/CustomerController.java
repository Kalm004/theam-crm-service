package com.aromero.theamcrmservice.customer;

import com.aromero.theamcrmservice.user.User;
import com.aromero.theamcrmservice.user.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/customers")
public class CustomerController {
    @Autowired
    private CustomerService customerService;

    @Autowired
    private UserService userService;

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    @GetMapping
    public List<Customer> getAll() {
        return customerService.getAllCustomers();
    }

    @GetMapping("/{id}")
    public Customer getById(@PathVariable(value = "id") Long id) {
        Optional<Customer> customer = customerService.getCustomerById(id);
        if (customer.isPresent()) {
            return customer.get();
        } else {
            throw new EntityNotFoundException("Customer with id " + id + " not found");
        }
    }

    @PostMapping
    public void createCustomer(@RequestBody CustomerImportDTO customer) {
        //TODO: get the user that did the request
        User user = userService.getUserById(1L).orElseThrow(EntityNotFoundException::new);
        customerService.saveCustomer(convertToEntity(customer), user);
    }

    @PutMapping("/{id}")
    public void updateCustomer(@PathVariable(value = "id") Long id, @RequestBody CustomerImportDTO customer) {
        //TODO: get the user that did the request
        User user = userService.getUserById(1L).orElseThrow(EntityNotFoundException::new);
        customerService.saveCustomer(convertToEntity(customer), user);
    }

    @DeleteMapping("/{id}")
    public void deleteCustomer(@PathVariable(value = "id") Long id) {
        customerService.deleteCustomer(id);
    }

    private Customer convertToEntity(CustomerImportDTO customerImportDTO) {
        Customer customer = modelMapper().map(customerImportDTO, Customer.class);

        if (customerImportDTO.getId() != null) {
            Customer oldCustomer = customerService.getCustomerById(customerImportDTO.getId())
                    .orElseThrow(EntityNotFoundException::new);
            customer.setCreatedByUser(oldCustomer.getCreatedByUser());
        }
        return customer;
    }
}
