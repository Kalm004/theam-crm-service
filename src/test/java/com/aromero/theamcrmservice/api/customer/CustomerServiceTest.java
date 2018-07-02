package com.aromero.theamcrmservice.api.customer;

import com.aromero.theamcrmservice.api.customer.dto.CreateCustomerRequest;
import com.aromero.theamcrmservice.api.customer.dto.CustomerResponse;
import com.aromero.theamcrmservice.api.customer.dto.UpdateCustomerRequest;
import com.aromero.theamcrmservice.storage.Storage;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import javax.persistence.EntityNotFoundException;
import java.util.List;

import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CustomerServiceTest {
    private static final int NUMBER_OF_CUSTOMERS = 2;

    @MockBean
    private Storage storage;

    @Autowired
    private CustomerService customerService;

    @Before
    public void before() {

    }

    @Test
    public void getAllCustomers() {
        List<CustomerResponse> allCustomers = customerService.getAllCustomers();

        Assert.assertEquals(NUMBER_OF_CUSTOMERS, allCustomers.size());
    }

    @Test
    public void getCustomerByIdFound() {
        when(storage.getTempLink("/customers/1/customer1.jpg")).thenReturn("http://test.com/customer.1jpg");
        CustomerResponse customer = customerService.getCustomerById(1L);

        Assert.assertNotNull(customer.getCreatedByUser());
        Assert.assertNull(customer.getModifiedByUser());
        Assert.assertEquals("http://test.com/customer.1jpg", customer.getPhotoTempUrl());
    }

    @Test
    public void getCustomerByIdCreatedByDeletedUser() {
        CustomerResponse customer = customerService.getCustomerById(2L);

        Assert.assertNotNull(customer.getCreatedByUser());
        Assert.assertEquals((Long)3L, customer.getCreatedByUser().getId());
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
    public void deleteCustomerWithPhoto() {
        customerService.deleteCustomer(1L);

        List<CustomerResponse> customerList = customerService.getAllCustomers();
        Assert.assertEquals(NUMBER_OF_CUSTOMERS - 1, customerList.size());
    }

    @Test
    @DirtiesContext
    public void deleteCustomerWithoutPhoto() {
        customerService.deleteCustomer(2L);

        List<CustomerResponse> customerList = customerService.getAllCustomers();
        Assert.assertEquals(NUMBER_OF_CUSTOMERS - 1, customerList.size());
    }

    @Test
    public void deleteCustomerNotFound() {
        try {
            customerService.deleteCustomer(100L);
            Assert.fail("An EntityNotFoundException should has been thrown");
        } catch (EntityNotFoundException e) {
            Assert.assertTrue(true);
        }
    }
}
