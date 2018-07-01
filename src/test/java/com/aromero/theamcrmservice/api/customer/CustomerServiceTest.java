package com.aromero.theamcrmservice.api.customer;

import com.aromero.theamcrmservice.api.customer.dto.CreateCustomerRequest;
import com.aromero.theamcrmservice.api.customer.dto.CustomerResponse;
import com.aromero.theamcrmservice.api.customer.dto.UpdateCustomerRequest;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CustomerServiceTest {
    @Autowired
    private CustomerService customerService;

    @Test
    public void getAllCustomers() {
        List<CustomerResponse> allCustomers = customerService.getAllCustomers();

        Assert.assertEquals(1, allCustomers.size());
    }

    @Test
    public void getCustomerByIdFound() {
        CustomerResponse customer = customerService.getCustomerById(1L);

        Assert.assertNotNull(customer.getCreatedByUser());
        Assert.assertNull(customer.getModifiedByUser());
    }

    @Test
    public void getCustomerByNotFound() {
        try {
            CustomerResponse customer = customerService.getCustomerById(100L);
            Assert.fail("An EntityNotFoundException should has been thrown");
        } catch (EntityNotFoundException e) {
            Assert.assertTrue(true);
        }
    }

    @Test
    @DirtiesContext
    public void addCustomer() {
        CreateCustomerRequest customer = new CreateCustomerRequest();
        customer.setName("Name2");
        customer.setSurname("Surname2");

        CustomerResponse modifiedCustomerResponse = customerService.createCustomer(customer, 1L);
        Assert.assertNotNull(modifiedCustomerResponse.getId());

        List<CustomerResponse> customerList = customerService.getAllCustomers();
        Assert.assertTrue(customerList.stream().anyMatch(c -> c.getName().equals("Name2")));
    }

    @Test
    @DirtiesContext
    public void modifyCustomer() {
        CustomerResponse customerFromDatabase = customerService.getCustomerById(1L);

        UpdateCustomerRequest updateCustomerRequest = new UpdateCustomerRequest();
        updateCustomerRequest.setName("ModifiedName");
        updateCustomerRequest.setSurname(customerFromDatabase.getSurname());

        customerService.updateCustomer(1L, updateCustomerRequest, 1L);

        CustomerResponse modifiedCustomer = customerService.getCustomerById(1L);

        Assert.assertEquals("ModifiedName", modifiedCustomer.getName());
    }

    @Test
    @DirtiesContext
    public void deleteCustomer() {
        customerService.deleteCustomer(1L);

        List<CustomerResponse> customerList = customerService.getAllCustomers();
        Assert.assertTrue(customerList.isEmpty());
    }
}
