package com.aromero.theamcrmservice.integration;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;
import static io.restassured.RestAssured.when;
import static org.hamcrest.core.IsEqual.equalTo;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@RunWith(SpringRunner.class)
public class UserIntegrationTest {
    @LocalServerPort
    private Integer port;

    private String baseUrl;

    @Before
    public void before() {
        baseUrl = "http://localhost:" + port;
    }

    @Test
    public void getAllUsers200AndGetListOfUsers() {
        when().
            get(baseUrl + "/user").
        then().
            statusCode(200).
            body("size()", equalTo(1));
    }
}

