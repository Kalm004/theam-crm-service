package com.aromero.theamcrmservice.integration;

import com.aromero.theamcrmservice.api.auth.dto.LoginRequest;
import com.aromero.theamcrmservice.api.auth.dto.LoginResponse;
import io.restassured.http.ContentType;
import io.restassured.http.Header;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;

import static io.restassured.RestAssured.given;
import static org.hamcrest.core.IsEqual.equalTo;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CustomerIntegrationTest {
    @LocalServerPort
    private Integer port;

    private String baseUrl;

    private LoginResponse loginResponse;

    @Before
    public void before() {
        baseUrl = "http://localhost:" + port;

        loginResponse =
            given().
                contentType(ContentType.JSON).
                body(new LoginRequest("user1@domain.com", "test")).
            post(baseUrl + "/login").
                body().
                as(LoginResponse.class);
    }

    @Test
    public void getAllCustomers200AndGetListOfCustomers() {
        given().
            header(new Header("Authorization", "Bearer " + loginResponse.getToken())).
        when().
            get(baseUrl + "/customers").
        then().
            statusCode(200).
            body("size()", equalTo(1));
    }

    @Test
    public void getCustomerById200AndCustomerWithExistingCustomer() {
        given().
                header(new Header("Authorization", "Bearer " + loginResponse.getToken())).
        when().
            get(baseUrl + "/customers/{id}", 1).
        then().
            statusCode(200).
            body("id", equalTo(1));
    }
}
