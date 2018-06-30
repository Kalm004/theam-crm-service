package com.aromero.theamcrmservice.customer;

import com.aromero.theamcrmservice.security.CurrentUser;
import com.aromero.theamcrmservice.security.CustomUserDetails;
import com.aromero.theamcrmservice.user.User;
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
    public void createCustomer(@RequestBody CustomerImportDTO customer, @CurrentUser CustomUserDetails customUserDetails) {
        User user = new User();
        user.setId(customUserDetails.getId());
        customerService.saveCustomer(convertToEntity(customer), user);
    }

    @PutMapping("/{id}")
    public void updateCustomer(@PathVariable(value = "id") Long id, @RequestBody CustomerImportDTO customer,
                               @CurrentUser CustomUserDetails customUserDetails) {
        User user = new User();
        user.setId(customUserDetails.getId());
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
