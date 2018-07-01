package com.aromero.theamcrmservice.api.customer;

import com.aromero.theamcrmservice.api.customer.dto.CreateCustomerRequest;
import com.aromero.theamcrmservice.api.customer.dto.CustomerResponse;
import com.aromero.theamcrmservice.api.customer.dto.UpdateCustomerRequest;
import com.aromero.theamcrmservice.api.customer.mapper.CreateCustomerRequestMapper;
import com.aromero.theamcrmservice.api.customer.mapper.CustomerResponseMapper;
import com.aromero.theamcrmservice.api.customer.mapper.UpdateCustomerRequestMapper;
import com.aromero.theamcrmservice.storage.Storage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CustomerService {
    private final CustomerRepository customerRepository;

    private final CustomerResponseMapper customerResponseMapper;

    private final CreateCustomerRequestMapper createCustomerRequestMapper;

    private final UpdateCustomerRequestMapper updateCustomerRequestMapper;

    private final Storage storage;

    @Autowired
    public CustomerService(
            CustomerRepository customerRepository,
            CustomerResponseMapper customerResponseMapper,
            CreateCustomerRequestMapper createCustomerRequestMapper,
            UpdateCustomerRequestMapper updateCustomerRequestMapper,
            Storage storage) {
        this.customerRepository = customerRepository;
        this.customerResponseMapper = customerResponseMapper;
        this.createCustomerRequestMapper = createCustomerRequestMapper;
        this.updateCustomerRequestMapper = updateCustomerRequestMapper;
        this.storage = storage;
    }

    public List<CustomerResponse> getAllCustomers() {
        return convertCustomerListToCustomerResponseList((List<Customer>) customerRepository.findAll());
    }

    public CustomerResponse getCustomerById(Long id) {
        return customerResponseMapper.mapToCustomerResponse(getCustomerByIdOrThrow(id));
    }

    public CustomerResponse createCustomer(CreateCustomerRequest createCustomerRequest, Long createdByUserId) {
        Customer customer = createCustomerRequestMapper.mapToCustomer(createCustomerRequest, createdByUserId);

        customerRepository.save(customer);

        return customerResponseMapper.mapToCustomerResponse(customer);
    }

    @Transactional
    public void updateCustomer(Long customerId, UpdateCustomerRequest updateCustomerRequest, Long modifiedByUserId) {
        Customer customerFromDatabase = getCustomerByIdOrThrow(customerId);

        Customer customerToBeSaved = updateCustomerRequestMapper.mapToCustomer(updateCustomerRequest, modifiedByUserId);

        customerToBeSaved.setId(customerId);
        customerToBeSaved.setCreatedByUser(customerFromDatabase.getCreatedByUser());

        customerRepository.save(customerToBeSaved);
    }

    @Transactional
    public void deleteCustomer(Long id) {
        getCustomerByIdOrThrow(id);

        customerRepository.deleteById(id);
    }

    @Transactional
    public void saveCustomerPhoto(Long customerId, MultipartFile file, Long modifiedByUserId) throws IOException {
        Customer customer = getCustomerByIdOrThrow(customerId);

        storage.saveFile("/customers/" + customerId + "/" + file.getOriginalFilename(), file.getInputStream());

        customer.setPhotoFileName(file.getOriginalFilename());
        customer.setModifiedByUser(modifiedByUserId);

        customerRepository.save(customer);
    }

    @Transactional
    public String getTempLink(Long customerId) {
        Customer customer = getCustomerByIdOrThrow(customerId);

        if (customer.getPhotoFileName() == null) {
            throw new EntityNotFoundException("Customer doesn't have photo");
        }

        return storage.getTempLink("/customers/" + customerId + "/" + customer.getPhotoFileName());
    }

    private Customer getCustomerByIdOrThrow(Long id) {
        Optional<Customer> customer = customerRepository.findById(id);

        if (!customer.isPresent()) {
            throw new EntityNotFoundException("Customer with id " + id + " not found");
        }

        return customer.get();
    }

    private List<CustomerResponse> convertCustomerListToCustomerResponseList(List<Customer> customers) {
        return customers
                .stream()
                .map(customerResponseMapper::mapToCustomerResponse)
                .collect(Collectors.toList());
    }
}
