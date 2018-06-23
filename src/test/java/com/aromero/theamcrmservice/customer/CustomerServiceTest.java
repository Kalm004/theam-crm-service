package com.aromero.theamcrmservice.customer;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Optional;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CustomerServiceTest {
    @Autowired
    private CustomerService customerService;

    @Test
    public void getAllCustomers() {
        List<Customer> allCustomers = customerService.getAllCustomers();

        Assert.assertEquals(1, allCustomers.size());
    }

    @Test
    public void getCustomerByIdFound() {
        Optional<Customer> customer = customerService.getCustomerById(1L);

        Assert.assertTrue(customer.isPresent());
    }

    @Test
    public void getCustomerByNotFound() {
        Optional<Customer> customer = customerService.getCustomerById(100L);

        Assert.assertFalse(customer.isPresent());
    }
}
