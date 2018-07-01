package com.aromero.theamcrmservice.api.customer;

import com.aromero.theamcrmservice.api.customer.dto.CreateCustomerRequest;
import com.aromero.theamcrmservice.api.customer.dto.CustomerResponse;
import com.aromero.theamcrmservice.api.customer.dto.UpdateCustomerRequest;
import com.aromero.theamcrmservice.security.CurrentUser;
import com.aromero.theamcrmservice.security.CustomUserDetails;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/customers")
public class CustomerController {
    private final CustomerService customerService;

    @Autowired
    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    @GetMapping
    public List<CustomerResponse> getAll() {
        return customerService.getAllCustomers();
    }

    @GetMapping("/{id}")
    public CustomerResponse getById(@PathVariable(value = "id") Long id) {
        return customerService.getCustomerById(id);
    }

    @PostMapping
    public CustomerResponse createCustomer(
            @RequestBody CreateCustomerRequest createCustomerRequest,
            @CurrentUser CustomUserDetails customUserDetails
    ) {
        return customerService.createCustomer(createCustomerRequest, customUserDetails.getId());
    }

    @PutMapping("/{id}")
    public void updateCustomer(
            @PathVariable(value = "id") Long customerId,
            @Valid @RequestBody UpdateCustomerRequest updateCustomerRequest,
            @CurrentUser CustomUserDetails customUserDetails
    ) {
        customerService.updateCustomer(customerId, updateCustomerRequest, customUserDetails.getId());
    }

    @DeleteMapping("/{id}")
    public void deleteCustomer(@PathVariable(value = "id") Long id) {
        customerService.deleteCustomer(id);
    }

    @PostMapping("/{id}/photo")
    public void handleFileUpload(
            @PathVariable("id") Long customerId,
            @RequestParam("file") MultipartFile file,
            @CurrentUser CustomUserDetails currentUser
    ) throws IOException {
        customerService.saveCustomerPhoto(customerId, file, currentUser.getId());
    }

    @GetMapping("/{id}/photo")
    public String getPhotoUrl(@PathVariable("id") Long customerId) {
        return customerService.getTempLink(customerId);
    }
}
