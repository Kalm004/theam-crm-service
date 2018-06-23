package com.aromero.theamcrmservice.integration;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import static io.restassured.RestAssured.when;
import static org.hamcrest.core.IsEqual.equalTo;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserIntegrationTest {
    @Test
    public void getAllUsers200AndGetListOfUsers() {
        when().
            get("/user").
        then().
            statusCode(200).
            body("size()", equalTo(1));
    }
}
