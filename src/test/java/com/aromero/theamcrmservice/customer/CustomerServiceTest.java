package com.aromero.theamcrmservice.customer;

import com.aromero.theamcrmservice.user.User;
import com.aromero.theamcrmservice.user.UserService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CustomerServiceTest {
    @Autowired
    private CustomerService customerService;

    @Autowired
    private UserService userService;

    @Test
    public void getAllCustomers() {
        List<Customer> allCustomers = customerService.getAllCustomers();

        Assert.assertEquals(1, allCustomers.size());
    }

    @Test
    public void getCustomerByIdFound() {
        Optional<Customer> customer = customerService.getCustomerById(1L);

        Assert.assertTrue(customer.isPresent());
        Assert.assertNull(customer.get().getModifiedByUser());
    }

    @Test
    public void getCustomerByNotFound() {
        Optional<Customer> customer = customerService.getCustomerById(100L);

        Assert.assertFalse(customer.isPresent());
    }

    @Test
    @DirtiesContext
    public void addCustomer() {
        Customer customer = new Customer();
        customer.setName("Name2");
        customer.setSurname("Surname2");
        customer.setCreatedByUser(userService.getUserById(1L).get());

        User user = userService.getUserById(1L).orElseThrow(EntityNotFoundException::new);

        customerService.saveCustomer(customer, user);

        List<Customer> customerList = customerService.getAllCustomers();
        Assert.assertTrue(customerList.stream().anyMatch(c -> c.getName().equals("Name2")));
    }

    @Test
    @DirtiesContext
    public void modifyCustomer() {
        Optional<Customer> customer = customerService.getCustomerById(1L);

        Assert.assertTrue(customer.isPresent());

        customer.get().setName("ModifiedName");

        User user = userService.getUserById(1L).orElseThrow(EntityNotFoundException::new);

        customerService.saveCustomer(customer.get(), user);

        Optional<Customer> modifiedCustomer = customerService.getCustomerById(1L);

        Assert.assertTrue(modifiedCustomer.isPresent());
        Assert.assertEquals("ModifiedName", modifiedCustomer.get().getName());
    }

    @Test
    @DirtiesContext
    public void deleteCustomer() {
        customerService.deleteCustomer(1L);

        List<Customer> customerList = customerService.getAllCustomers();
        Assert.assertTrue(customerList.isEmpty());
    }
}
