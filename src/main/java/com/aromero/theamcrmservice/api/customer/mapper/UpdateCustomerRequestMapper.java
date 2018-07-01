package com.aromero.theamcrmservice.api.customer.mapper;

import com.aromero.theamcrmservice.api.customer.Customer;
import com.aromero.theamcrmservice.api.customer.dto.UpdateCustomerRequest;
import org.modelmapper.ModelMapper;

public class UpdateCustomerRequestMapper {
    private ModelMapper modelMapper;

    public UpdateCustomerRequestMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public Customer mapToCustomer(UpdateCustomerRequest updateCustomerRequest, Long modifiedByUserId) {
        Customer customer = modelMapper.map(updateCustomerRequest, Customer.class);

        customer.setModifiedByUser(modifiedByUserId);

        return customer;
    }
}
