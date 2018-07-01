package com.aromero.theamcrmservice.api.customer.mapper;

import com.aromero.theamcrmservice.api.customer.Customer;
import com.aromero.theamcrmservice.api.customer.dto.CreateCustomerRequest;
import org.modelmapper.ModelMapper;

public class CreateCustomerRequestMapper {
    private ModelMapper modelMapper;

    public CreateCustomerRequestMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public Customer mapToCustomer(CreateCustomerRequest createCustomerRequest, Long createdByUserId) {
        Customer customer = modelMapper.map(createCustomerRequest, Customer.class);

        customer.setCreatedByUser(createdByUserId);

        return customer;
    }
}
