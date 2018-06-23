package com.aromero.theamcrmservice.integration;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;

import static io.restassured.RestAssured.when;
import static org.hamcrest.core.IsEqual.equalTo;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CustomerIntegrationTest {
    @LocalServerPort
    private Integer port;

    private String baseUrl;

    @Before
    public void before() {
        baseUrl = "http://localhost:" + port;
    }

    @Test
    public void getAllCustomers200AndGetListOfCustomers() {
        when().
            get(baseUrl + "/customer").
        then().
            statusCode(200).
            body("size()", equalTo(1));
    }

    @Test
    public void getCustomerById200AndCustomerWithExistingCustomer() {
        when().
            get(baseUrl + "/customer/{id}", 1).
        then().
            statusCode(200).
            body("id", equalTo(1));
    }
}
