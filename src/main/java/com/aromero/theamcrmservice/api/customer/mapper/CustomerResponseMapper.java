package com.aromero.theamcrmservice.api.customer.mapper;

import com.aromero.theamcrmservice.api.customer.Customer;
import com.aromero.theamcrmservice.api.customer.dto.CustomerResponse;
import org.modelmapper.ModelMapper;

public class CustomerResponseMapper {
    private ModelMapper modelMapper;

    public CustomerResponseMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public CustomerResponse mapToCustomerResponse(Customer customer) {
        return modelMapper.map(customer, CustomerResponse.class);
    }
}
