package com.aromero.theamcrmservice.api.customerPhoto;

import com.aromero.theamcrmservice.api.customer.Customer;
import com.aromero.theamcrmservice.api.customer.CustomerService;
import com.aromero.theamcrmservice.api.user.User;
import com.aromero.theamcrmservice.security.CustomUserDetails;
import com.aromero.theamcrmservice.storage.Storage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityNotFoundException;
import java.io.IOException;
import java.util.Optional;

@Service
public class CustomerPhotoService {
    private final Storage storage;
    private final CustomerService customerService;

    @Autowired
    public CustomerPhotoService(Storage storage, CustomerService customerService) {
        this.storage = storage;
        this.customerService = customerService;
    }

    @Transactional
    public void saveCustomerPhoto(Long customerId, MultipartFile file, CustomUserDetails currentUser) throws IOException {
        Optional<Customer> customer = customerService.getCustomerById(customerId);
        if (!customer.isPresent()) {
            throw new EntityNotFoundException("Customer doesn't exist");
        }

        storage.saveFile("/customers/" + customerId + "/" + file.getOriginalFilename(), file.getInputStream());

        customer.get().setPhotoFileName(file.getOriginalFilename());

        User user = new User();
        user.setId(currentUser.getId());

        //TODO: create a new update customer photo filename method in the customer service
        customerService.saveCustomer(customer.get(), user);
    }

    @Transactional
    public String getTempLink(Long customerId) {
        //TODO: create a new get customer photo filename method in the customer service
        Optional<Customer> customer = customerService.getCustomerById(customerId);
        if (!customer.isPresent() || customer.get().getPhotoFileName() == null) {
            throw new EntityNotFoundException("Customer doesn't exist or it doesn't have photo");
        }

        return storage.getTempLink("/customers/" + customerId + "/" + customer.get().getPhotoFileName());
    }
}
